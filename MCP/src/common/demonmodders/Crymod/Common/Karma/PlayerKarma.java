package demonmodders.Crymod.Common.Karma;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;

public class PlayerKarma {
	
	private float karma = 0;
	
	private byte[] eventAmounts = new byte[CountableKarmaEvent.values().length];
	
	private final EntityPlayer player;
	
	public PlayerKarma(EntityPlayer player) {
		this.player = player;
	}
	
	public PlayerKarma() {
		this(null);
	}
	
	public float getKarma() {
		return karma;
	}
	
	public void setKarma(float karma) {
		this.karma = karma;
		if (this.karma > PlayerKarmaManager.MAX_KARMA_VALUE) {
			this.karma = PlayerKarmaManager.MAX_KARMA_VALUE;
		}
		if (this.karma < -PlayerKarmaManager.MAX_KARMA_VALUE) {
			this.karma = -PlayerKarmaManager.MAX_KARMA_VALUE;
		}
		PlayerKarmaManager.instance().updateClientKarma(player);
	}
	
	public float modifyKarma(float modifier) {
		player.worldObj.playSoundAtEntity(player, "summoningmod.karma" + (modifier < 0 ? "down" : "up"), 1, 1);
		
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
		eventAmounts[event.ordinal()] = (byte)amount;
		PlayerKarmaManager.instance().updateClientKarma(player);
	}
	
	public void increaseEventAmount(CountableKarmaEvent event) {
		setEventAmount(event, eventAmounts[event.ordinal()] + 1);
	}
	
	public void write(ByteArrayDataOutput out) {
		out.writeFloat(karma);
		for (byte amount : eventAmounts) {
			out.writeByte(amount);
		}
	}
	
	public PlayerKarma read(ByteArrayDataInput in) {
		karma = in.readFloat();
		
		for (int i = 0; i < eventAmounts.length; i++) {
			eventAmounts[i] = in.readByte();
		}
		
		return this;
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
	
	public PlayerKarma read(NBTTagCompound nbt) {
		karma = nbt.getFloat("karma");
		NBTTagList eventList = nbt.getTagList("events");
		for (int i = 0; i < eventList.tagCount(); i++) {
			NBTTagCompound evtInfo = (NBTTagCompound)eventList.tagAt(i);
			byte eventId = evtInfo.getByte("id");
			if (eventId >= 0 && eventId < eventAmounts.length) {
				eventAmounts[eventId] = evtInfo.getByte("value");
			}
		}
		return this;
	}
	
	public static PlayerKarma create(ByteArrayDataInput in) {
		return new PlayerKarma().read(in);
	}
	
	public static enum CountableKarmaEvent {
		PIGMEN_ATTACK;
	}
}