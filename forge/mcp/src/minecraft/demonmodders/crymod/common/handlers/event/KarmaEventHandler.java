package demonmodders.crymod.common.handlers.event;

import static demonmodders.crymod.common.playerinfo.PlayerInformation.forPlayer;

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
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
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
import cpw.mods.fml.relauncher.ReflectionHelper;
import demonmodders.crymod.common.CrymodUtils;
import demonmodders.crymod.common.playerinfo.PlayerInformation;
import demonmodders.crymod.common.playerinfo.PlayerInformation.CountableKarmaEvent;

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
				forPlayer(player).modifyKarmaWithMax(0.1F, 20);
			} else if (evt.entity instanceof EntityVillager) {
				forPlayer(player).modifyKarmaWithMin(-0.1F, -20);
			} else if (evt.entity instanceof EntityWitch) {
				forPlayer(player).modifyKarmaWithMax(0.5F, 49);
			} else if (evt.entity instanceof EntityIronGolem || evt.entity instanceof EntitySnowman) {
				forPlayer(player).modifyKarmaWithMin(-0.5F, -20);
			} else if (evt.entity instanceof EntityDragon) {
				forPlayer(player).modifyKarma(6);
			}
		}
	}
	
	@ForgeSubscribe
	public void onEntityAttack(LivingAttackEvent evt) {
		if (evt.source instanceof EntityDamageSource && ((EntityDamageSource)evt.source).getEntity() instanceof EntityPlayerMP && evt.entity instanceof EntityPigZombie) {
			PlayerInformation info = forPlayer((EntityPlayer)((EntityDamageSource)evt.source).getEntity());
			if (info.getEventAmount(PlayerInformation.CountableKarmaEvent.PIGMEN_ATTACK) == 0) {
				info.increaseEventAmount(PlayerInformation.CountableKarmaEvent.PIGMEN_ATTACK);
				info.modifyKarma(-1);
			}
		}
	}
	
	@ForgeSubscribe
	public void onItemDestroy(PlayerDestroyItemEvent evt) {
		if (evt.entityPlayer instanceof EntityPlayerMP && evt.original.itemID == Item.potion.itemID && ItemPotion.isSplash(evt.original.getItemDamage())) {
			List<PotionEffect> effects = Item.potion.getEffects(evt.original);
			for (PotionEffect effect : effects) {
				if (Potion.heal.id == effect.getPotionID()) {
					forPlayer(evt.entityPlayer).modifyKarmaWithMax(1, 30);
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
			forPlayer(evt.entityPlayer).modifyKarmaWithMax(0.1F, 10);
		}
	}
	
	@ForgeSubscribe
	public void onEntityInteract(EntityInteractEvent evt) {
		if (evt.entityPlayer instanceof EntityPlayerMP) {
			if (evt.target instanceof EntityAnimal) {
				EntityAnimal animal = (EntityAnimal)evt.target;
				if (animal.isBreedingItem(evt.entityPlayer.getCurrentEquippedItem()) && animal.getGrowingAge() == 0) {
					CrymodUtils.getEntityData(animal).setString("breedingOwner", evt.entityPlayer.username);
				}
			} else if (evt.target instanceof EntityZombie) {
				EntityZombie zombie = (EntityZombie)evt.target;
				ItemStack currentItem = evt.entityPlayer.getCurrentEquippedItem();
				if (currentItem != null && currentItem.getItem() == Item.appleGold && currentItem.getItemDamage() == 0 && zombie.isVillager() && zombie.isPotionActive(Potion.weakness)) {
					CrymodUtils.getEntityData(zombie).setString("cureOwner", evt.entityPlayer.username);
				}
			}
		}
	}
	
	// called by the hook inserted into EntityAIMate by the CrymodTransformer 
	public static void onBreedingSpawnChild(EntityAgeable baby, EntityAIMate aiInstance) {
		EntityAnimal animal = ReflectionHelper.getPrivateValue(EntityAIMate.class, aiInstance, 0);
		EntityAnimal mate = ReflectionHelper.getPrivateValue(EntityAIMate.class, aiInstance, 2);
		String animalBreeder = CrymodUtils.getEntityData(animal).getString("breedingOwner");
		String mateBreeder = CrymodUtils.getEntityData(mate).getString("breedingOwner");
		
		if (animalBreeder.equals(mateBreeder)) {
			EntityPlayer player = animal.worldObj.getPlayerEntityByName(animalBreeder);
			if (player != null) {
				forPlayer(player).modifyKarmaWithMax(0.1F, 20);
			}
		}
	}
	
	// called by the hook inserted into EntityZombie by the CrymodTransformer
	public static void onZombieConvert(EntityZombie zombie) {
		String curer = CrymodUtils.getEntityData(zombie).getString("cureOwner");
		EntityPlayer player = zombie.worldObj.getPlayerEntityByName(curer);
		if (player != null) {
			forPlayer(player).modifyKarmaWithMax(3, 30);
		}
	}
	
	// called by the hook inserted into Block/onBlockHarvested by the CrymodTransformer
	public static void onPlayerBlockHarvest(Block block, World world, EntityPlayer player) {
		if (!world.isRemote && block.blockID == Block.mobSpawner.blockID) {
			forPlayer(player).modifyKarmaWithMax(1, 30);
		}
	}
	
	// called by the hook inserted into ItemStack/tryPlaceItemIntoWorld
	public static void onTryPlaceItem(ItemStack item, EntityPlayer player, int x, int y, int z, int side, boolean success) {
		if (!player.worldObj.isRemote && item.itemID == Block.pumpkin.blockID && success) {
			int blockId = player.worldObj.getBlockId(x, y, z);
			if (blockId == Block.snow.blockID) {
				side = 1;
			} else if (blockId != Block.vine.blockID && blockId != Block.tallGrass.blockID && blockId != Block.deadBush.blockID && (Block.blocksList[blockId] == null || !Block.blocksList[blockId].isBlockReplaceable(player.worldObj, x, y, z))) {
				switch (side) {
				case 0:
					y--;
					break;
				case 1:
					y++;
					break;
				case 2:
					z--;
					break;
				case 3:
					z++;
					break;
				case 4:
					x--;
					break;
				case 5:
					x++;
					break;
				}
			}
			System.out.println(player.worldObj.getBlockId(x, y - 1, z));
			
			if (player.worldObj.getBlockId(x, y - 1, z) == Block.blockSnow.blockID && player.worldObj.getBlockId(x, y - 2, z) == Block.blockSnow.blockID) {
				PlayerInformation info = forPlayer(player);
				if (info.getEventAmount(CountableKarmaEvent.CREATE_SNOWGOLEM) < 2) {
					info.increaseEventAmount(CountableKarmaEvent.CREATE_SNOWGOLEM);
					info.modifyKarma(1);
				}
			}
		}
	}
	
	public static void onBeforePlayerPlaceBlock(ItemBlock item, World world, int x, int y, int z, EntityPlayer player) {
		System.out.println("place");
		if (!world.isRemote && item.getBlockID() == Block.pumpkin.blockID) {
			System.out.println("place pumpkin on server");
			// first check for snow golem
			if (world.getBlockId(x, y - 1, z) == Block.blockSnow.blockID && world.getBlockId(x, y - 2, z) == Block.blockSnow.blockID) {
				PlayerInformation info = forPlayer(player);
				if (info.getEventAmount(PlayerInformation.CountableKarmaEvent.CREATE_SNOWGOLEM) < 2) {
					info.increaseEventAmount(PlayerInformation.CountableKarmaEvent.CREATE_SNOWGOLEM);
					info.modifyKarma(1);
				}
			}
		}
	}
	
	private static final int[] MESSAGE_BORDERS = {50, 40, 25, 10};
	
	@ForgeSubscribe
	public void onServerChat(ServerChatEvent evt) {
		float karma = forPlayer(evt.player).getKarma();
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
			String messageKey = "crymod.chatprefix." + (karma < 0 ? "bad" : "good") + "." + useBorder;
			String messageColor = karma < 0 ? "4" : "1";
			evt.line = evt.line.replace(evt.username, CrymodUtils.color(StringTranslate.getInstance().translateKey(messageKey), messageColor, "f") +  " " + evt.username);
		}
	}
}
