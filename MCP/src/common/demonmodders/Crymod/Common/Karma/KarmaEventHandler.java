package demonmodders.Crymod.Common.Karma;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityDamageSource;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EntityVillager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;

public class KarmaEventHandler implements ITickHandler {
	
	private KarmaEventHandler() {}
	
	public static void init() {
		KarmaEventHandler instance = new KarmaEventHandler();
		MinecraftForge.EVENT_BUS.register(instance);
		TickRegistry.registerTickHandler(instance, Side.SERVER);		
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
	
	@ForgeSubscribe
	public void onEntityInteract(EntityInteractEvent evt) {
		if (evt.entityPlayer instanceof EntityPlayerMP && evt.target instanceof EntityAnimal) {
			EntityAnimal animal = (EntityAnimal)evt.target;
			if (animal.isBreedingItem(evt.entityPlayer.getCurrentEquippedItem()) && animal.getGrowingAge() == 0) {
				BreedingInfo info = breedingInfo.get(evt.entityPlayer);
				if (info == null || info.animal == animal) {
					breedingInfo.put(evt.entityPlayer, new BreedingInfo(animal, animal.inLove));
				} else {
					if (info.animal.getDistanceSqToEntity(animal) <= 64) {
						PlayerKarmaManager.instance().getPlayerKarma(evt.entityPlayer).modifyKarmaWithMax(1, 10);
						breedingInfo.remove(evt.entityPlayer);
					} else {
						breedingInfo.put(evt.entityPlayer, new BreedingInfo(animal, animal.inLove));
					}
				}
			}
		}
	}
	
	private static class BreedingInfo {
		private final EntityAnimal animal;
		private int ticks;
		
		private BreedingInfo(EntityAnimal animal, int ticks) {
			this.animal = animal;
			this.ticks = ticks;
		}
	}
	
	private Map<EntityPlayer,BreedingInfo> breedingInfo = new HashMap<EntityPlayer,BreedingInfo>();

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		Iterator<Entry<EntityPlayer,BreedingInfo>> it = breedingInfo.entrySet().iterator();
		
		while (it.hasNext()) {
			Entry<EntityPlayer,BreedingInfo> entry = it.next();
			
			entry.getValue().ticks--;
			if (entry.getValue().ticks == 0 || entry.getKey().isDead) {
				it.remove();
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.SERVER);
	}

	@Override
	public String getLabel() {
		return "SummoningModKarmaManager";
	}
}
