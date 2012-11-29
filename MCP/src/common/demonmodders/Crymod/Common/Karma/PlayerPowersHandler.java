package demonmodders.Crymod.Common.Karma;

import demonmodders.Crymod.Common.PlayerInfo.PlayerInfo;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class PlayerPowersHandler {
	
	private PlayerPowersHandler() {}
	
	public static void init() {
		MinecraftForge.EVENT_BUS.register(new PlayerPowersHandler());
	}
	
	@ForgeSubscribe
	public void onLivingFall(LivingFallEvent evt) {
		if (evt.entity instanceof EntityPlayerMP && ensureMinKarma(evt.entity, 25)) {
			evt.setCanceled(true);
		}
	}
	
	@ForgeSubscribe
	public void onLivingAttack(LivingAttackEvent evt) {
		if (evt.entity instanceof EntityPlayerMP && ensureMinKarma(evt.entity, -25)) {
			if (evt.source.isFireDamage() || evt.source == DamageSource.drown) {
				evt.setCanceled(true);
			}
		}
	}
	
	private boolean ensureMinKarma(Entity ent, int minKarma) {
		return PlayerInfo.forPlayer((EntityPlayer)ent).getKarma().getKarma() >= minKarma;
	}
	
	private boolean ensureMaxKarma(Entity ent, int maxKarma) {
		return PlayerInfo.forPlayer((EntityPlayer)ent).getKarma().getKarma() <= maxKarma;
	}
}