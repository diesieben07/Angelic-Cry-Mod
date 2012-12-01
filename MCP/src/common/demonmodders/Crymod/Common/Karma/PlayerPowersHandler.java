package demonmodders.Crymod.Common.Karma;

import java.util.EnumSet;

import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import demonmodders.Crymod.Common.PlayerInfo.PlayerInfo;

public class PlayerPowersHandler implements IScheduledTickHandler {
	
	private static final PlayerPowersHandler instance = new PlayerPowersHandler();
	
	public static PlayerPowersHandler instance() {
		return instance;
	}
	
	private PlayerPowersHandler() {}
	
	public static void init() {
		MinecraftForge.EVENT_BUS.register(instance);
		TickRegistry.registerScheduledTickHandler(instance, Side.SERVER);
	}
	
	@ForgeSubscribe
	public void onLivingFall(LivingFallEvent evt) {
		if (evt.entity instanceof EntityPlayerMP && ensureMinKarma(evt.entity, 25)) {
			evt.setCanceled(true);
		}
	}
	
	@ForgeSubscribe
	public void onLivingAttack(LivingAttackEvent evt) {
		if (evt.entity instanceof EntityPlayerMP && ensureMaxKarma(evt.entity, -25)) {
			if (evt.source.isFireDamage() || evt.source == DamageSource.drown) {
				evt.setCanceled(true);
			}
		}
	}
	
	@ForgeSubscribe
	public void onEntityAttackTarget(LivingSetAttackTargetEvent evt) {
		if (evt.target != null && evt.target instanceof EntityPlayerMP && evt.entity instanceof EntityMob && ensureMaxKarma(evt.target, -40)) {
			evt.entityLiving.setAttackTarget(null);
		}
	}
	
	private boolean ensureMinKarma(Entity ent, int minKarma) {
		return PlayerInfo.forPlayer((EntityPlayer)ent).getKarma().getKarma() >= minKarma;
	}
	
	private boolean ensureMaxKarma(Entity ent, int maxKarma) {
		return PlayerInfo.forPlayer((EntityPlayer)ent).getKarma().getKarma() <= maxKarma;
	}
	
	public void onPlayerInvisibilityRequest(EntityPlayer player) {
		PlayerInfo info = PlayerInfo.forPlayer(player);
		int cooldown = info.getInvisibilityCooldown();
		if (cooldown == 0 && info.getKarma().getKarma() <= -50) {
			info.setInvisibilityCooldown(INVISIBILITY_COOLDOWN);
			player.addPotionEffect(new PotionEffect(Potion.invisibility.id, INVISIBILITY_TIME));
		}
	}
	
	private static final int TICKS_PER_SECOND = 20;
	private static final int FLYING_TIME = 30;
	private static final int FLYING_COOLDOWN = 100;
	
	private static final int INVISIBILITY_TIME = 30 * TICKS_PER_SECOND;
	private static final int INVISIBILITY_COOLDOWN = 100;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {		
		EntityPlayer player = (EntityPlayer)tickData[0];
		PlayerInfo info = PlayerInfo.forPlayer(player);
		
		int invisCooldown = info.getInvisibilityCooldown();
		if (invisCooldown > 0) {
			invisCooldown--;
		}
		
		info.setInvisibilityCooldown(invisCooldown);
		
		int flyTime = info.getFlyTime();
		
		if (player.capabilities.isCreativeMode) {
			info.setFlyTime(0);
			return;
		} else if (info.getKarma().getKarma() < 50) {
			info.setFlyTime(-1);
			if (player.capabilities.allowFlying) {
				player.capabilities.isFlying = player.capabilities.allowFlying = false;
				player.sendPlayerAbilities();
			}
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
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		PlayerInfo.forPlayer((EntityPlayer) tickData[0]).onUpdate();
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "SummoningmodPlayerPowers";
	}

	@Override
	public int nextTickSpacing() {
		return TICKS_PER_SECOND;
	}
}