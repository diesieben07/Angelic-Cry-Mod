package demonmodders.crymod.common.playerinfo;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import demonmodders.crymod.common.CrymodUtils;
import demonmodders.crymod.common.network.PacketPlayerInfo;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public final class PlayerInformation {

	public static void handlePlayerDataSave(EntityPlayer player, NBTTagCompound nbt) {
		forPlayer(player).writeToNbt(nbt);
	}
	
	public static void handlePlayerDataLoad(EntityPlayer player, NBTTagCompound nbt) {
		forPlayer(player).readFromNbt(nbt);
	}
	
	public static void handlePlayerDataCopy(EntityPlayer target, EntityPlayer source) {
		try {
			PlayerInformation sourceInfo = forPlayer(source);
			playerInfo.set(target, sourceInfo); // TODO: maybe possibly remove reflection
			sourceInfo.setDirty();
		} catch (ReflectiveOperationException e) {
			// nope
		}
	}
	
	public static void handlePlayerConstruct(EntityPlayer player) {
		try {
			playerInfo.set(player, new PlayerInformation(player));
		} catch (ReflectiveOperationException e) {
			// nope
		}
	}

	private static Field playerInfo;
	
	static {
		 try {
			playerInfo = EntityPlayer.class.getDeclaredField("summoningmodPlayerInfo");
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}
	
	public static PlayerInformation forPlayer(EntityPlayer player) {
		try {
			return (PlayerInformation)playerInfo.get(player);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static final int MAX_KARMA_VALUE = 50;
	
	private boolean dirty = true;
	private float karma;
	private byte[] eventAmounts = new byte[PlayerInformation.CountableKarmaEvent.values().length];
	
	private final EntityPlayer player;

	private int invisibilityCooldown;

	private int flyTime;
	
	private byte[] enderbookRecipes = new byte[0];
	
	public PlayerInformation(EntityPlayer player) {
		this.player = player;
	}
	
	public void writeToNbt(NBTTagCompound nbt) {
		nbt.setFloat("karma", karma);
		nbt.setShort("flyTime", (short) flyTime);
		nbt.setShort("invisCooldown", (short) invisibilityCooldown);
		
		NBTTagList eventList = new NBTTagList();
		for (int i = 0; i < eventAmounts.length; i++) {
			NBTTagCompound evtInfo = new NBTTagCompound();
			evtInfo.setByte("id", (byte)i);
			evtInfo.setByte("value", eventAmounts[i]);
			eventList.appendTag(evtInfo);
		}
		nbt.setTag("events", eventList);
		
		nbt.setByteArray("enderBook", enderbookRecipes);
	}
	
	public void readFromNbt(NBTTagCompound nbt) {
		karma = nbt.getFloat("karma");
		flyTime = nbt.getShort("flyTime");
		invisibilityCooldown = nbt.getShort("invisCooldown");
		
		NBTTagList eventList = nbt.getTagList("events");
		for (int i = 0; i < eventList.tagCount(); i++) {
			NBTTagCompound evtInfo = (NBTTagCompound)eventList.tagAt(i);
			byte eventId = evtInfo.getByte("id");
			if (eventId >= 0 && eventId < eventAmounts.length) {
				eventAmounts[eventId] = evtInfo.getByte("value");
			}
		}
		
		enderbookRecipes = nbt.getByteArray("enderBook");
	}
	
	public float getKarma() {
		return karma;
	}
	
	public float setKarma(float karma) {
		if (this.karma != karma) {
			this.karma = karma;
			if (this.karma > MAX_KARMA_VALUE) {
				this.karma = MAX_KARMA_VALUE;
			}
			if (this.karma < -MAX_KARMA_VALUE) {
				this.karma = -MAX_KARMA_VALUE;
			}
			setDirty();
		}
		return this.karma;
	}
	
	public float modifyKarma(float modifier) {
		player.worldObj.playSoundAtEntity(player, "summoningmod.karma" + (modifier < 0 ? "down" : "up"), 1, 1);
		
		return setKarma(karma + modifier);
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
	
	public byte getEventAmount(PlayerInformation.CountableKarmaEvent event) {
		return eventAmounts[event.ordinal()];
	}
	
	public void setEventAmount(PlayerInformation.CountableKarmaEvent event, int amount) {
		if (eventAmounts[event.ordinal()] != amount) {
			eventAmounts[event.ordinal()] = (byte)amount;
			setDirty();
		}
	}
	
	public void increaseEventAmount(PlayerInformation.CountableKarmaEvent event) {
		setEventAmount(event, eventAmounts[event.ordinal()] + 1);
	}
	
	public static enum CountableKarmaEvent {
		PIGMEN_ATTACK, CREATE_SNOWGOLEM, CREATE_IRONGOLEM;
	}
	
	public int getFlyTime() {
		return flyTime;
	}
	
	public void setFlyTime(int flyTime) {
		if (this.flyTime != flyTime) {
			this.flyTime = flyTime;
			setDirty();
		}
	}

	public int getInvisibilityCooldown() {
		return invisibilityCooldown;
	}
	
	public void setInvisibilityCooldown(int cooldown) {
		if (invisibilityCooldown != cooldown) {
			invisibilityCooldown = cooldown;
		}
	}
	
	public byte[] getEnderBookRecipesRaw() {
		return enderbookRecipes;
	}
	
	public SummoningRecipe[] getEnderBookRecipes() {
		SummoningRecipe[] recipes = new SummoningRecipe[enderbookRecipes.length];
		for (int i = 0; i < enderbookRecipes.length; i++) {
			recipes[i] = SummoningRecipe.byId(enderbookRecipes[i]);
		}
		return recipes;
	}
	
	public void setEnderBookRecipes(byte[] recipes) {
		enderbookRecipes = recipes;
	}
	
	public void addEnderBookRecipe(byte recipe) {
		if (!hasEnderBookRecipe(recipe)) {
			enderbookRecipes = Arrays.copyOf(enderbookRecipes, enderbookRecipes.length + 1);
			enderbookRecipes[enderbookRecipes.length - 1] = recipe;
			setDirty();
		}
	}
	
	public boolean hasEnderBookRecipe(byte recipe) {
		for (byte _recipe : enderbookRecipes) {
			if (recipe == _recipe) {
				return true;
			}
		}
		return false;
	}
	
	public void tick() {
		if (dirty) {
			updateClient();System.out.println("resending packet");
			dirty = false;
		}
	}
	
	private void updateClient() {
		new PacketPlayerInfo(this).sendToPlayer(player);
	}
	
	/**
	 * marks that this needs to be resend to the client
	 */
	public void setDirty() {
		dirty = true;
	}
}