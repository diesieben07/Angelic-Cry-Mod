package demonmodders.crymod.common.handlers.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import demonmodders.crymod.common.playerinfo.PlayerInformation;

public class PlayerPowersHandler {
	
	private static final PlayerPowersHandler instance = new PlayerPowersHandler();
	
	public static PlayerPowersHandler instance() {
		return instance;
	}
	
	private PlayerPowersHandler() {}
	
	public static final int TICKS_PER_SECOND = 20;
	public static final int FLYING_TIME = 30 * TICKS_PER_SECOND;
	public static final int FLYING_COOLDOWN = 100 * TICKS_PER_SECOND;
	
	public static final int INVISIBILITY_TIME = 30 * TICKS_PER_SECOND;
	public static final int INVISIBILITY_COOLDOWN = 130 * TICKS_PER_SECOND;
	
	public static void init() {
		MinecraftForge.EVENT_BUS.register(instance);
	}
	
	@ForgeSubscribe
	public void onLivingFall(LivingFallEvent evt) {
		if (evt.entity instanceof EntityPlayerMP && ensureMinKarma(evt.entity, 25)) {
			evt.setCanceled(true);
		}
	}
	
	@ForgeSubscribe
	public void onLivingAttack(LivingAttackEvent evt) {
		if (evt.entity instanceof EntityPlayerMP && (evt.source.isFireDamage() || evt.source == DamageSource.drown) && ensureMaxKarma(evt.entity, -25)) {
			evt.setCanceled(true);
		}
	}
	
	@ForgeSubscribe
	public void onLivingHurt(LivingHurtEvent evt) {
		if (evt.entity instanceof EntityPlayerMP && ensureMinKarma(evt.entity, 10)) {
			evt.ammount -= 1;
		} else if (evt.source instanceof EntityDamageSource) {
			EntityDamageSource source = (EntityDamageSource)evt.source;
			if (source.getEntity() instanceof EntityPlayerMP && ensureMaxKarma(source.getEntity(), -10)) {
				evt.ammount += 1;
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
		return PlayerInformation.forPlayer((EntityPlayer)ent).getKarma() >= minKarma;
	}
	
	private boolean ensureMaxKarma(Entity ent, int maxKarma) {
		return PlayerInformation.forPlayer((EntityPlayer)ent).getKarma() <= maxKarma;
	}
	
	public void onPlayerInvisibilityRequest(EntityPlayer player) {
		PlayerInformation info = PlayerInformation.forPlayer(player);
		int cooldown = info.getInvisibilityCooldown();
		if (cooldown == 0 && info.getKarma() <= -50) {
			info.setInvisibilityCooldown(INVISIBILITY_COOLDOWN);
			player.addPotionEffect(new PotionEffect(Potion.invisibility.id, INVISIBILITY_TIME));
		}
	}
}