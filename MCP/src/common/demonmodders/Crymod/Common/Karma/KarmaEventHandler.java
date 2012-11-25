package demonmodders.Crymod.Common.Karma;

import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityDamageSource;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EntityVillager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class KarmaEventHandler {
	private KarmaEventHandler() {}
	
	public static void init() {
		MinecraftForge.EVENT_BUS.register(new KarmaEventHandler());
	}
	
	@ForgeSubscribe
	public void onEntityInteract(EntityInteractEvent evt) {
		if (evt.entityPlayer instanceof EntityPlayerMP) {
			if (evt.target instanceof EntityAnimal && ((EntityAnimal)evt.target).isBreedingItem(evt.entityPlayer.getCurrentEquippedItem())) {
				PlayerKarmaManager.instance().getPlayerKarma(evt.entityPlayer).modifyKarmaWithMax(1, 10);
			}
		}
	}
	
	@ForgeSubscribe
	public void onEntityDeath(LivingDeathEvent evt) {
		if (evt.source instanceof EntityDamageSource && ((EntityDamageSource)evt.source).getEntity() instanceof EntityPlayerMP) {
			EntityPlayer player = (EntityPlayer)((EntityDamageSource)evt.source).getEntity();
			if (evt.entity instanceof EntityCreeper) {
				PlayerKarmaManager.instance().getPlayerKarma(player).modifyKarmaWithMax(1, 10);
			} else if (evt.entity instanceof EntityVillager) {
				PlayerKarmaManager.instance().getPlayerKarma(player).modifyKarmaWithMin(-2, -20);
			}
		}
	}
}
