package demonmodders.crymod.common.recipes;

import static demonmodders.crymod.common.items.CrystalType.*;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
	
	public SummoningRecipe(int id, ItemStack specialItem, CrystalType... crystals) {
		this(id, new ItemStack[] { specialItem }, crystals);
	}
	
	public SummoningRecipe(int id, ItemStack[] specialItems, CrystalType... crystals) {
		super(false);
		if (crystals.length != 9) {
			throw new IllegalArgumentException();
		}
		
		this.id = id;
		
		stacks = new ItemStack[specialItems.length + 9];
		
		if (crystals.length != 9) {
			throw new IllegalArgumentException("Invalid Summoning Recipe!");
		}
		
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
			).setSummonerType(
			Type.SUMMONING_BOOK).setMinKarma(5);
	
	public static final SummoningRecipe HELL_ZOMBIE = new SummoningRecipeDemonAngel(
			1, EntityHellZombie.class, "Hell Zombie", Item.redstone, 
			FOREST, 
			LEAF, LEAF,
			FOREST, MAGIC, FOREST,
			LEAF, LEAF,
			FOREST
			).setMaxKarma(-5).setSummonerType(
			Type.EVIL_TABLET);
	
	public static final SummoningRecipe SHEEP = new SummoningRecipeAnimals(2, EntitySheep.class, Block.cloth, Block.stone, Block.snow).setSummonerType(Type.SUMMONING_BOOK);

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