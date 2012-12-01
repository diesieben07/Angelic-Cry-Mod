package demonmodders.Crymod.Common.Karma;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import demonmodders.Crymod.Common.PlayerInfo.PlayerInfo;

public class PlayerKarma {
	
	public static final int MAX_KARMA_VALUE = 50;
	
	private float karma = 0;
	
	private byte[] eventAmounts = new byte[CountableKarmaEvent.values().length];
	
	private final PlayerInfo info;
	
	public PlayerKarma(float karma) {
		this.karma = karma;
		info = null;
	}
	
	public PlayerKarma(PlayerInfo info) {
		this.info = info;
	}
	
	public float getKarma() {
		return karma;
	}
	
	public void setKarma(float karma) {
		if (this.karma != karma) {
			this.karma = karma;
			if (this.karma > MAX_KARMA_VALUE) {
				this.karma = MAX_KARMA_VALUE;
			}
			if (this.karma < -MAX_KARMA_VALUE) {
				this.karma = -MAX_KARMA_VALUE;
			}
			info.onChange();
		}
	}
	
	public float modifyKarma(float modifier) {
		info.player.worldObj.playSoundAtEntity(info.player, "summoningmod.karma" + (modifier < 0 ? "down" : "up"), 1, 1);
		
		setKarma(karma + modifier);
		return karma;
	}
	
	public float modifyKarmaWithMax(float modifier, float max) {
		if (karma < max) {
			modifyKarma(modifier);
		}
		return karma;
	}
	
	public float modifyKarmaWithMin(float modifier, float min) {
		if (karma > min) {
			modifyKarma(modifier);
		}
		return karma;
	}
	
	public byte getEventAmount(CountableKarmaEvent event) {
		return eventAmounts[event.ordinal()];
	}
	
	public void setEventAmount(CountableKarmaEvent event, int amount) {
		if (eventAmounts[event.ordinal()] != amount) {
			eventAmounts[event.ordinal()] = (byte)amount;
			info.onChange();
		}
	}
	
	public void increaseEventAmount(CountableKarmaEvent event) {
		setEventAmount(event, eventAmounts[event.ordinal()] + 1);
	}
	
	public static enum CountableKarmaEvent {
		PIGMEN_ATTACK;
	}

	public void write(NBTTagCompound nbt) {
		nbt.setFloat("karma", karma);
		NBTTagList eventList = new NBTTagList();
		for (int i = 0; i < eventAmounts.length; i++) {
			NBTTagCompound evtInfo = new NBTTagCompound();
			evtInfo.setByte("id", (byte)i);
			evtInfo.setByte("value", eventAmounts[i]);
			eventList.appendTag(evtInfo);
		}
		nbt.setTag("events", eventList);
	}
	
	public void read(NBTTagCompound nbt) {
		karma = nbt.getFloat("karma");
		NBTTagList eventList = nbt.getTagList("events");
		for (int i = 0; i < eventList.tagCount(); i++) {
			NBTTagCompound evtInfo = (NBTTagCompound)eventList.tagAt(i);
			byte eventId = evtInfo.getByte("id");
			if (eventId >= 0 && eventId < eventAmounts.length) {
				eventAmounts[eventId] = evtInfo.getByte("value");
			}
		}
	}
}