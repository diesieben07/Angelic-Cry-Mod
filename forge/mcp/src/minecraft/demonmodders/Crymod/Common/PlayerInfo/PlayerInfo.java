package demonmodders.Crymod.Common.PlayerInfo;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.registry.GameRegistry;
import demonmodders.Crymod.Common.Karma.PlayerKarma;
import demonmodders.Crymod.Common.Network.PacketPlayerInfo;

public final class PlayerInfo {
	
	private static Map<EntityPlayer, PlayerInfo> infoMap = new HashMap<EntityPlayer, PlayerInfo>();
	
	public static PlayerInfo forPlayer(EntityPlayer player) {
		if (!infoMap.containsKey(player)) {
			infoMap.put(player, new PlayerInfo(player));
		}
		return infoMap.get(player);
	}
	
	public static PlayerKarma playerKarma(EntityPlayer player) {
		return forPlayer(player).getKarma();
	}
	
	public static void init() {
		GameRegistry.registerPlayerTracker(new IPlayerTracker() {
			
			@Override
			public void onPlayerRespawn(EntityPlayer player) {
				EntityPlayer oldPlayer = null;
				for (EntityPlayer oldPlayerSearch : infoMap.keySet()) {
					if (oldPlayerSearch.username.equals(player.username)) {
						oldPlayer = oldPlayerSearch;
					}
				}
				if (oldPlayer != null) {
					infoMap.remove(oldPlayer);
				}
			}
			
			@Override
			public void onPlayerLogout(EntityPlayer player) {
				infoMap.remove(player).updatePlayerNbt();
			}
			
			@Override
			public void onPlayerLogin(EntityPlayer player) {
				forPlayer(player).updateClient();
			}
			
			@Override
			public void onPlayerChangedDimension(EntityPlayer player) { }
		});
	}
	
	public final EntityPlayer player;
	private PlayerKarma karma;
	
	private int flyTime;
	private int invisibilityCooldown;
	private boolean isDirty = true;
	
	private PlayerInfo(EntityPlayer player) {
		this.player = player;
		karma = new PlayerKarma(this);
		NBTTagCompound infoNbt = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag("summoningmod");
		karma.read(infoNbt);
		flyTime = infoNbt.getByte("flyTime");
		invisibilityCooldown = infoNbt.getByte("invisCooldown");
	}
	
	public PlayerInfo(PlayerKarma karma, int flyTime) {
		this.karma = karma;
		this.flyTime = flyTime;
		player = null;
	}
	
	public PlayerKarma getKarma() {
		return karma;
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
			updatePlayerNbt();
		}
	}
	
	private void updateClient() {
		new PacketPlayerInfo(this).sendToPlayer(player);
	}
	
	private void updatePlayerNbt() {
		NBTTagCompound infoNbt = new NBTTagCompound();
		
		karma.write(infoNbt);
		
		infoNbt.setByte("flyTime", (byte)flyTime);
		
		infoNbt.setByte("invisCooldown", (byte)invisibilityCooldown);
		
		NBTTagCompound persistedNbt = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		persistedNbt.setCompoundTag("summoningmod", infoNbt);
		player.getEntityData().setCompoundTag(EntityPlayer.PERSISTED_NBT_TAG, persistedNbt);
	}
	
	public void setDirty() {
		isDirty = true;
	}
	
	public void onUpdate() {
		if (isDirty) {
			updateClient();
			updatePlayerNbt();
			isDirty = false;
		}
	}
}