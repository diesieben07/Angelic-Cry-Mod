package demonmodders.Crymod.Common.Karma;

import static demonmodders.Crymod.Common.Karma.PlayerKarmaManager.playerKarma;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.src.Block;
import net.minecraft.src.BlockCrops;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntityDamageSource;
import net.minecraft.src.EntityDragon;
import net.minecraft.src.EntityIronGolem;
import net.minecraft.src.EntityPigZombie;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EntitySnowman;
import net.minecraft.src.EntityVillager;
import net.minecraft.src.EntityWitch;
import net.minecraft.src.EntityZombie;
import net.minecraft.src.Item;
import net.minecraft.src.ItemPotion;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import demonmodders.Crymod.Common.Karma.PlayerKarma.CountableKarmaEvent;

public class KarmaEventHandler implements ITickHandler {
	
	private static final KarmaEventHandler instance = new KarmaEventHandler();
	
	public static KarmaEventHandler instance() {
		return instance;
	}
	
	private KarmaEventHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		TickRegistry.registerTickHandler(this, Side.SERVER);
	}
	
	@ForgeSubscribe
	public void onEntityDeath(LivingDeathEvent evt) {
		if (evt.source instanceof EntityDamageSource && ((EntityDamageSource)evt.source).getEntity() instanceof EntityPlayerMP) {
			EntityPlayer player = (EntityPlayer)((EntityDamageSource)evt.source).getEntity();
			
			if (evt.entity instanceof EntityCreeper) {
				playerKarma(player).modifyKarmaWithMax(0.1F, 20);
			} else if (evt.entity instanceof EntityVillager) {
				playerKarma(player).modifyKarmaWithMin(-0.1F, -20);
			} else if (evt.entity instanceof EntityWitch) {
				playerKarma(player).modifyKarmaWithMax(0.5F, 49);
			} else if (evt.entity instanceof EntityIronGolem || evt.entity instanceof EntitySnowman) {
				playerKarma(player).modifyKarmaWithMin(-0.5F, -20);
			} else if (evt.entity instanceof EntityDragon) {
				playerKarma(player).modifyKarma(6);
			}
		}
	}
	
	@ForgeSubscribe
	public void onEntityAttack(LivingAttackEvent evt) {
		if (evt.source instanceof EntityDamageSource && ((EntityDamageSource)evt.source).getEntity() instanceof EntityPlayerMP && evt.entity instanceof EntityPigZombie) {
			PlayerKarma karma = playerKarma((EntityPlayer)((EntityDamageSource)evt.source).getEntity());
			if (karma.getEventAmount(CountableKarmaEvent.PIGMEN_ATTACK) == 0) {
				karma.increaseEventAmount(CountableKarmaEvent.PIGMEN_ATTACK);
				karma.modifyKarma(-1);
			}
		}
	}
	
	@ForgeSubscribe
	public void onItemDestroy(PlayerDestroyItemEvent evt) {
		if (evt.entityPlayer instanceof EntityPlayerMP && evt.original.getItem().shiftedIndex == Item.potion.shiftedIndex && ItemPotion.isSplash(evt.original.getItemDamage())) {
			List<PotionEffect> effects = Item.potion.getEffects(evt.original);
			for (PotionEffect effect : effects) {
				if (Potion.heal.id == effect.getPotionID()) {
					playerKarma(evt.entityPlayer).modifyKarmaWithMax(1, 30);
					return;
				}
			}
		}
	}
	
	private static final List<Integer> bonemealHandleIds = Arrays.asList(
			Block.sapling.blockID,
			Block.mushroomBrown.blockID,
			Block.mushroomRed.blockID,
			Block.melonStem.blockID,
			Block.pumpkinStem.blockID,
			Block.cocoaPlant.blockID,
			Block.grass.blockID			
		);
	
	// set priority to lowest to also give good karma when other mods handle bonemeal event
	@ForgeSubscribe(priority = EventPriority.LOWEST)
	public void onBonemealUse(BonemealEvent evt) {
		if (evt.getResult() == Result.ALLOW || bonemealHandleIds.contains(evt.ID) || (evt.ID > 0 && Block.blocksList[evt.ID] instanceof BlockCrops)) { 
			playerKarma(evt.entityPlayer).modifyKarmaWithMax(0.1F, 10);
		}
	}
	
	@ForgeSubscribe
	public void onEntityInteract(EntityInteractEvent evt) {
		if (evt.entityPlayer instanceof EntityPlayerMP) {
			if (evt.target instanceof EntityAnimal) {
				EntityAnimal animal = (EntityAnimal)evt.target;
				if (animal.isBreedingItem(evt.entityPlayer.getCurrentEquippedItem()) && animal.getGrowingAge() == 0) {
					EntityAnimal animalPrev = breedingInfo.get(evt.entityPlayer);
					if (animalPrev == null || animalPrev == animal) {
						breedingInfo.put(evt.entityPlayer, animal);
					} else {
						if (animalPrev.getDistanceSqToEntity(animal) <= 64) {
							playerKarma(evt.entityPlayer).modifyKarmaWithMax(0.1F, 20);
							breedingInfo.remove(evt.entityPlayer);
						} else {
							breedingInfo.put(evt.entityPlayer, animal);
						}
					}
				}
			} else if (evt.target instanceof EntityZombie) {
				EntityZombie zombie = (EntityZombie)evt.target;
				ItemStack currentItem = evt.entityPlayer.getCurrentEquippedItem();
				if (currentItem != null && currentItem.getItem() == Item.appleGold && currentItem.getItemDamage() == 0 && zombie.isVillager() && zombie.isPotionActive(Potion.weakness)) {
					startZombieCure(evt.entityPlayer, (EntityZombie) evt.target);
				}
			}
		}
	}
	
	private void startZombieCure(EntityPlayer player, EntityZombie zombie) {
		if (!zombieCureInfo.containsKey(player)) {
			zombieCureInfo.put(player, new HashSet<EntityZombie>());
		}
		zombieCureInfo.get(player).add(zombie);
	}
	
	private Map<EntityPlayer,EntityAnimal> breedingInfo = new HashMap<EntityPlayer,EntityAnimal>();
	private Map<EntityPlayer,Set<EntityZombie>> zombieCureInfo = new HashMap<EntityPlayer, Set<EntityZombie>>();

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// update breeding infos
		Iterator<Entry<EntityPlayer,EntityAnimal>> it = breedingInfo.entrySet().iterator();
		
		while (it.hasNext()) {
			Entry<EntityPlayer,EntityAnimal> entry = it.next();
			
			if (entry.getValue().inLove == 0 || entry.getValue().isDead || entry.getKey().isDead) {
				it.remove();
			}
		}
		
		// update zombie cure infos
		Iterator<Entry<EntityPlayer,Set<EntityZombie>>> itZombieInfo = zombieCureInfo.entrySet().iterator();
		while (itZombieInfo.hasNext()) {
			Entry<EntityPlayer,Set<EntityZombie>> entry = itZombieInfo.next();
			if (entry.getKey().isDead) {
				itZombieInfo.remove();
			} else {
				Iterator<EntityZombie> itZombies = entry.getValue().iterator();
				while (itZombies.hasNext()) {
					EntityZombie zombie = itZombies.next();
					if (zombie.isDead || getZombieCureTime(zombie) == 0) {
						playerKarma(entry.getKey()).modifyKarmaWithMax(3, 30);
						itZombies.remove();
					}
				}
				if (entry.getValue().isEmpty()) {
					itZombieInfo.remove();
				}
			}
		}
	}
	
	private int getZombieCureTime(EntityZombie zombie) {
		return ReflectionHelper.getPrivateValue(EntityZombie.class, zombie, 0);
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
