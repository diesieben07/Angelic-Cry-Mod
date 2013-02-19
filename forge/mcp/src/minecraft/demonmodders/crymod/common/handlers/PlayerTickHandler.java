package demonmodders.crymod.common.handlers;

import static demonmodders.crymod.common.handlers.event.PlayerPowersHandler.FLYING_COOLDOWN;
import static demonmodders.crymod.common.handlers.event.PlayerPowersHandler.FLYING_TIME;
import static demonmodders.crymod.common.handlers.event.PlayerPowersHandler.TICKS_PER_SECOND;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import demonmodders.crymod.common.playerinfo.PlayerInformation;

public class PlayerTickHandler implements IScheduledTickHandler {

	private static final String PROFILER_SECTION = "summoningmodPlayerHandling";
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {		
		EntityPlayer player = (EntityPlayer)tickData[0];
		player.worldObj.theProfiler.startSection(PROFILER_SECTION);
		PlayerInformation info = PlayerInformation.forPlayer(player);
		
		int invisCooldown = info.getInvisibilityCooldown();
		if (invisCooldown > 0) {
			invisCooldown--;
		}
		
		info.setInvisibilityCooldown(invisCooldown);
		
		int flyTime = info.getFlyTime();
		
		if (player.capabilities.isCreativeMode) {
			info.setFlyTime(0);
			player.worldObj.theProfiler.endSection();
			return;
		} else if (info.getKarma() < 50) {
			info.setFlyTime(-1);
			if (player.capabilities.allowFlying) {
				player.capabilities.isFlying = player.capabilities.allowFlying = false;
				player.sendPlayerAbilities();
			}
			player.worldObj.theProfiler.endSection();
			return;
		}
		
		boolean mayFlyPrev = player.capabilities.allowFlying;
		boolean isFlyingPrev = player.capabilities.isFlying;		
		
		if (!player.capabilities.allowFlying) {
			player.capabilities.isFlying = false;
		}
		
		if (player.capabilities.isFlying || flyTime != 0) {
			flyTime++;
		}
		
		if (flyTime >= FLYING_TIME) {
			flyTime = -FLYING_COOLDOWN;
		}
		
		player.capabilities.allowFlying = flyTime >= 0;
		
		if (player.capabilities.allowFlying != mayFlyPrev || player.capabilities.isFlying != isFlyingPrev) {
			player.sendPlayerAbilities();
		}
		info.setFlyTime(flyTime);
		
		player.worldObj.theProfiler.endSection();
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		EntityPlayer player = (EntityPlayer)tickData[0];
		player.worldObj.theProfiler.startSection(PROFILER_SECTION);
		PlayerInformation.forPlayer(player).tick();
		player.worldObj.theProfiler.endSection();
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "SummonersServerTick";
	}

	@Override
	public int nextTickSpacing() {
		return 1;
	}
}