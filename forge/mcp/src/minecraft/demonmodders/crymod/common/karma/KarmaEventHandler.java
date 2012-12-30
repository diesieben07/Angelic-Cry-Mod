package demonmodders.crymod.common.karma;

import static demonmodders.crymod.common.playerinfo.PlayerInfo.playerKarma;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.StringTranslate;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import demonmodders.crymod.common.karma.PlayerKarma.CountableKarmaEvent;
import demonmodders.crymod.common.playerinfo.PlayerInfo;

public class KarmaEventHandler {
	
	public static void init() {
		new KarmaEventHandler();
	}
	
	private KarmaEventHandler() {
		MinecraftForge.EVENT_BUS.register(this);
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
					PlayerInfo.getModEntityData(animal).setString("breedingOwner", evt.entityPlayer.username);
				}
			} else if (evt.target instanceof EntityZombie) {
				EntityZombie zombie = (EntityZombie)evt.target;
				ItemStack currentItem = evt.entityPlayer.getCurrentEquippedItem();
				if (currentItem != null && currentItem.getItem() == Item.appleGold && currentItem.getItemDamage() == 0 && zombie.isVillager() && zombie.isPotionActive(Potion.weakness)) {
					PlayerInfo.getModEntityData(zombie).setString("cureOwner", evt.entityPlayer.username);
				}
			}
		}
	}
	
	// called by the hook inserted into EntityAIMate by the CrymodTransformer 
	public static void onBreedingSpawnChild(EntityAgeable baby, EntityAIMate aiInstance) {
		EntityAnimal animal = ReflectionHelper.getPrivateValue(EntityAIMate.class, aiInstance, 0);
		EntityAnimal mate = ReflectionHelper.getPrivateValue(EntityAIMate.class, aiInstance, 2);
		String animalBreeder = PlayerInfo.getModEntityData(animal).getString("breedingOwner");
		String mateBreeder = PlayerInfo.getModEntityData(mate).getString("breedingOwner");
		
		if (animalBreeder.equals(mateBreeder)) {
			EntityPlayer player = animal.worldObj.getPlayerEntityByName(animalBreeder);
			if (player != null) {
				playerKarma(player).modifyKarmaWithMax(0.1F, 20);
			}
		}
	}
	
	// called by the hook inserted into EntityZombie by the CrymodTransformer
	public static void onZombieConvert(EntityZombie zombie) {
		String curer = PlayerInfo.getModEntityData(zombie).getString("cureOwner");
		EntityPlayer player = zombie.worldObj.getPlayerEntityByName(curer);
		if (player != null) {
			playerKarma(player).modifyKarmaWithMax(3, 30);
		}
	}
	
	private static final int[] MESSAGE_BORDERS = {50, 40, 25, 10};
	
	@ForgeSubscribe
	public void onServerChat(ServerChatEvent evt) {
		float karma = playerKarma(evt.player).getKarma();
		if (karma == 0) {
			return;
		}
		int useBorder = -1;
		for (int border : MESSAGE_BORDERS) {
			if (karma > 0 && karma >= border || karma < 0 && karma <= -border && useBorder < border) {
				useBorder = border;
			}
		}
		if (useBorder != -1) {
			String messageKey = "chat.prefix." + (karma < 0 ? "bad" : "good") + "." + useBorder;
			String messageColor = karma < 0 ? "\u00A74" : "\u00A71";
			evt.line = evt.line.replace(evt.username, messageColor + StringTranslate.getInstance().translateKey(messageKey) + "\u00A7f " + evt.username);
		}
	}
}
