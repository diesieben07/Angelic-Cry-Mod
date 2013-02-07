package demonmodders.crymod.common.recipes;

import static demonmodders.crymod.common.items.CrystalType.*;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import demonmodders.crymod.common.entities.EntityHeavenZombie;
import demonmodders.crymod.common.entities.EntityHellZombie;
import demonmodders.crymod.common.gui.ContainerSummoner;
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
	
	public SummoningRecipe(int id, CrystalType... crystals) {
		super(true);
		this.id = id;
		
		if (crystals.length != 9) {
			throw new IllegalArgumentException("Invalid Summoning Recipe!");
		}
		
		for (int i = 0; i < 9; i++) {
			stacks[i] = crystals[i].generateItemStack();
		}
		
		recipes[id] = this;
	}
	
	public boolean matches(List<ItemStack> checkStacks, ContainerSummoner container) {
		if (checkStacks.size() != getSizeInventory() || container.getInventoryInstance().getType() != summonerType) {
			return false;
		} else {
			for (int i = 0; i < getSizeInventory(); i++) {
				ItemStack given = checkStacks.get(i);
				if (given == null && stacks[i] == null) {
					return true;
				}
				
				if (given == null || given.itemID != stacks[i].itemID || given.getItemDamage() != stacks[i].getItemDamage() || ItemCrystal.getCharge(given) == 0) {
					return false;
				}
			}
			return true;
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
		return 9;
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
			0, EntityHeavenZombie.class, "Heaven Zombie", OCEAN, OCEAN, OCEAN,
			OCEAN, OCEAN, OCEAN, OCEAN, OCEAN, OCEAN).setSummonerType(
			Type.SUMMONING_BOOK).setMinKarma(5);
	public static final SummoningRecipe HELL_ZOMBIE = new SummoningRecipeDemonAngel(
			1, EntityHellZombie.class, "Hell Zombie", CORE, CORE, CORE, CORE, CORE,
			CORE, CORE, CORE, CORE).setMaxKarma(-5).setSummonerType(
			Type.EVIL_TABLET);

	public static final SummoningRecipe SHEEP = new SummoningRecipeAnimals(2,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe COW = new SummoningRecipeAnimals(3,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe COW2 = new SummoningRecipeAnimals(4,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe COW4 = new SummoningRecipeAnimals(5,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe COW5 = new SummoningRecipeAnimals(6,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe COW3 = new SummoningRecipeAnimals(7,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe COW6 = new SummoningRecipeAnimals(8,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe A = new SummoningRecipeAnimals(9,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe B = new SummoningRecipeAnimals(10,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe Q = new SummoningRecipeAnimals(11,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe R = new SummoningRecipeAnimals(12,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe T = new SummoningRecipeAnimals(13,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe Z = new SummoningRecipeAnimals(14,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe U = new SummoningRecipeAnimals(15,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);
	
	public static final SummoningRecipe QW = new SummoningRecipeAnimals(16,
			EntitySheep.class, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF, LEAF,
			LEAF, LEAF).setSummonerType(Type.SUMMONING_BOOK);

	public static SummoningRecipe fromDamage(ItemStack stack) {
		return byId(stack.getItemDamage());
	}

	public static SummoningRecipe byId(int id) {
		return id < recipes.length && id >= 0 ? recipes[id] : null;
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