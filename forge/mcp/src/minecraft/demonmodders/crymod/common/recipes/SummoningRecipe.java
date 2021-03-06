package demonmodders.crymod.common.recipes;

import static demonmodders.crymod.common.items.CrystalType.CORE;
import static demonmodders.crymod.common.items.CrystalType.FIERY;
import static demonmodders.crymod.common.items.CrystalType.FOREST;
import static demonmodders.crymod.common.items.CrystalType.LEAF;
import static demonmodders.crymod.common.items.CrystalType.MAGIC;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import demonmodders.crymod.common.CrymodUtils;
import demonmodders.crymod.common.container.ContainerSummoner;
import demonmodders.crymod.common.entities.EntityHeavenZombie;
import demonmodders.crymod.common.entities.EntityHellZombie;
import demonmodders.crymod.common.inventory.AbstractInventory;
import demonmodders.crymod.common.items.CrystalType;
import demonmodders.crymod.common.items.ItemCrystal;
import demonmodders.crymod.common.items.ItemSummoner;
import demonmodders.crymod.common.items.ItemSummoner.Type;
import demonmodders.crymod.common.playerinfo.PlayerInformation;

public abstract class SummoningRecipe extends AbstractInventory {
	
	private final int id;
	
	private float minKarma = 0;
	private float maxKarma = 0;
	protected ItemSummoner.Type summonerType = Type.SUMMONING_BOOK;
	
	public SummoningRecipe(int id, Object specialItem, CrystalType... crystals) {
		this(id, new Object[] { specialItem }, crystals);
	}
	
	public SummoningRecipe(int id, Object[] specialItems, CrystalType... crystals) {
		super(false);
		if (crystals.length != 9) {
			throw new IllegalArgumentException();
		}
		
		this.id = id;
		
		stacks = new ItemStack[specialItems.length + 9];
		
		if (crystals.length != 9) {
			throw new IllegalArgumentException("Invalid Summoning Recipe!");
		}
		
		specialItems = CrymodUtils.getItemStacks(specialItems);
		
		System.arraycopy(specialItems, 0, stacks, 0, specialItems.length);
		
		for (int i = specialItems.length; i < specialItems.length + 9; i++) {
			stacks[i] = crystals[i - specialItems.length].generateItemStack();
		}
		
		recipes[id] = this;
	}
	
	public boolean matches(List<ItemStack> checkStacks, ContainerSummoner container) {
		if (checkStacks.size() != 10 || container.getInventoryInstance().getType() != summonerType) {
			return false;
		} else {
			int checkOffset = 1;
			for (int i = stacks.length - 9; i < stacks.length; i++) {
				ItemStack given = checkStacks.get(checkOffset);
				checkOffset++;
				if (given == null && stacks[i] == null) {
					return true;
				}
				
				if (given == null || given.itemID != stacks[i].itemID || given.getItemDamage() != stacks[i].getItemDamage() || ItemCrystal.getCharge(given) == 0) {
					return false;
				}
			}
			ItemStack givenSpecialItem = checkStacks.get(0);
			if (givenSpecialItem == null) {
				return false;
			}
			
			for (int i = 0; i < stacks.length - 9; i++) {
				if (givenSpecialItem.itemID == stacks[i].itemID && givenSpecialItem.getItemDamage() == stacks[i].getItemDamage()) {
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean canSummon(EntityPlayer player) {
		if (maxKarma == 0 && minKarma == 0) {
			return true;
		} else {
			float karma = PlayerInformation.forPlayer(player).getKarma();
			return maxKarma != 0 ? karma < maxKarma : karma > minKarma;
		}
	}
	
	final SummoningRecipe setMinKarma(float minKarma) {
		this.minKarma = minKarma;
		return this;
	}
	
	final SummoningRecipe setMaxKarma(float maxKarma) {
		this.maxKarma = maxKarma;
		return this;
	}
	
	public final ItemSummoner.Type getSummonerType() {
		return summonerType;
	}
	
	final SummoningRecipe setSummonerType(ItemSummoner.Type type) {
		summonerType = type;
		return this;
	}
	
	public int getColor() {
		return 0xffffff;
	}
	
	public String getChatColorCode() {
		return "f";
	}
	
	public final byte id() {
		return (byte)id;
	}
	
	public abstract EntityLiving summon(EntityPlayer player, ContainerSummoner container);
	
	public abstract String getRecipeName();
	
	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public String getInvName() {
		return "summoningRecipe";
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}
	
	public static SummoningRecipe[] recipes = new SummoningRecipe[32];
	
	public static final SummoningRecipe HEAVEN_ZOMBIE = new SummoningRecipeDemonAngel(
			0, EntityHeavenZombie.class, "Heaven Zombie", Item.redstone, 
			CORE, 
			FIERY, FIERY,
			CORE, MAGIC, CORE,
			FIERY, FIERY,
			CORE
			).setSummonerType(Type.SUMMONING_BOOK).setMinKarma(5);
	
	public static final SummoningRecipe HELL_ZOMBIE = new SummoningRecipeDemonAngel(
			1, EntityHellZombie.class, "Hell Zombie", Item.redstone, 
			FOREST, 
			LEAF, LEAF,
			FOREST, MAGIC, FOREST,
			LEAF, LEAF,
			FOREST
			).setMaxKarma(-5).setSummonerType(Type.EVIL_TABLET);
	
	public static final SummoningRecipe SHEEP = new SummoningRecipeAnimals(2, EntitySheep.class, Block.cloth).setSummonerType(Type.SUMMONING_BOOK);
	public static final SummoningRecipe COW = new SummoningRecipeAnimals(3, EntityCow.class, Item.leather).setSummonerType(Type.SUMMONING_BOOK);
	public static final SummoningRecipe PIG = new SummoningRecipeAnimals(4, EntityPig.class, Item.porkRaw).setSummonerType(Type.SUMMONING_BOOK);
	public static final SummoningRecipe CHICKEN = new SummoningRecipeAnimals(5, EntityChicken.class, Item.egg).setSummonerType(Type.SUMMONING_BOOK);
	public static final SummoningRecipe SQUID = new SummoningRecipeAnimals(6, EntitySquid.class, new ItemStack(Item.dyePowder, 1, 0)).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe ZOMBIE = new SummoningRecipeMobs(7, EntityZombie.class, Item.rottenFlesh);
	public static final SummoningRecipe SKELETON = new SummoningRecipeMobs(8, EntitySkeleton.class, Item.bone);
	public static final SummoningRecipe ENDERMAN = new SummoningRecipeMobs(9, EntityEnderman.class, Item.enderPearl);
	public static final SummoningRecipe CREEPER = new SummoningRecipeMobs(10, EntityCreeper.class, Item.gunpowder);
	
	public static final SummoningRecipe GHAST = new SummoningRecipeNetherMobs(11, EntityGhast.class, Item.ghastTear);
	public static final SummoningRecipe BLAZE = new SummoningRecipeNetherMobs(12, EntityBlaze.class, Item.blazeRod);
	public static final SummoningRecipe ZOMBIE_PIGMAN = new SummoningRecipeNetherMobs(13, EntityPigZombie.class, Item.ingotGold);
	public static final SummoningRecipe MAGMA_CUBE = new SummoningRecipeNetherMobs(14, EntityMagmaCube.class, Item.magmaCream);
	
	
	
	public static SummoningRecipe fromDamage(ItemStack stack) {
		return byId(stack.getItemDamage());
	}

	public static SummoningRecipe byId(int id) {
		return id < recipes.length && id >= 0 ? recipes[id] : SHEEP;
	}
	
	public static SummoningRecipe findMatchingRecipe(List<ItemStack> pattern, ContainerSummoner container) {
		for (SummoningRecipe recipe : recipes) {
			if (recipe != null && recipe.matches(pattern, container)) {
				return recipe;
			}
		}
		return null;
	}
}