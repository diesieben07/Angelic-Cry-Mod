package demonmodders.crymod.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public final class CrymodUtils {
	
	private CrymodUtils() {
		// static class, not instanciable
	}
	
	public static final String TEXTURE_FILE = "/demonmodders/crymod/resource/tex/textures.png";
	public static final String PARTICLE_TEXTURE_FILE = "/demonmodders/crymod/resource/tex/particles.png";
	
	public static final String COLOR_CODE = "\u00A7";
	private static final String CHAT_PREFIX = CrymodUtils.color("[Summoners] ", "2", "f");

	public static String getChatMessage(String message) {
		return CHAT_PREFIX + message;
	}

	public static String color(String message, String colorCode) {
		return COLOR_CODE + colorCode + message;
	}

	public static String color(String message, String colorCode, String resetColor) {
		return COLOR_CODE + colorCode + message + COLOR_CODE + resetColor;
	}

	public static NBTTagCompound getEntityData(Entity entity) {
		NBTTagCompound persistentData;
		
		if (entity instanceof EntityPlayer) {
			NBTTagCompound entityData = entity.getEntityData();
			persistentData = getCompound(entityData, EntityPlayer.PERSISTED_NBT_TAG);
		} else {
			persistentData = entity.getEntityData();
		}
		return getCompound(persistentData, "summoningmod");
	}
	
	public static NBTTagCompound getCompound(NBTTagCompound parent, String key) {
		if (!parent.hasKey(key)) {
			parent.setCompoundTag(key, new NBTTagCompound());
		}
		return parent.getCompoundTag(key);
	}
}