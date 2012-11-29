package demonmodders.Crymod.Common.Karma;

import demonmodders.Crymod.Common.PlayerInfo.PlayerInfo;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class PlayerPowersHandler {
	
	private PlayerPowersHandler() {}
	
	public static void init() {
		MinecraftForge.EVENT_BUS.register(new PlayerPowersHandler());
	}
	
	@ForgeSubscribe
	public void onEntityAttack(LivingAttackEvent evt) {
		if (evt.entity instanceof EntityPlayerMP && evt.source == DamageSource.fall) {
			evt.setCanceled(PlayerInfo.forPlayer((EntityPlayer)evt.entity).getKarma().getKarma() >= 25);
		}
	}
}