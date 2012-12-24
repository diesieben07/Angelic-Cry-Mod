package demonmodders.crymod.common.recipes;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import demonmodders.crymod.common.entities.EntityHeavenZombie;
import demonmodders.crymod.common.entities.EntityHellZombie;
import demonmodders.crymod.common.entities.SummonableBase;
import demonmodders.crymod.common.inventory.AbstractInventory;
import demonmodders.crymod.common.items.CrystalType;
import demonmodders.crymod.common.items.ItemCryMod;

import static demonmodders.crymod.common.items.CrystalType.*;

public class SummoningRecipe extends AbstractInventory {
	
	private final Class<? extends SummonableBase> demon;
	private final String demonName;
	private final int id;
	
	private boolean isAngel = false;
	
	public SummoningRecipe(int id, Class<? extends SummonableBase> demon, String demonName, CrystalType... crystals) {
		super(true);
		this.id = id;
		
		if (crystals.length != 9) {
			throw new IllegalArgumentException("Invalid Summoning Recipe!");
		}
		
		for (int i = 0; i < 9; i++) {
			stacks[i] = crystals[i].generateItemStack();
		}
		
		this.demon = demon;
		this.demonName = demonName;
		recipes[id] = this;
	}
	
	public boolean matches(List<ItemStack> checkStacks) {
		if (checkStacks.size() != getSizeInventory()) {
			return false;
		} else {
			
			for (int i = 0; i < getSizeInventory(); i++) {
				if (!ItemStack.areItemStacksEqual(stacks[i], checkStacks.get(i))) {
					return false;
				}
			}
			return true;
		}
	}
	
	final SummoningRecipe setAngel() {
		isAngel = true;
		return this;
	}
	
	public Class<? extends SummonableBase> getDemon() {
		return demon;
	}
	
	public final boolean isAngel() {
		return isAngel;
	}
	
	public String getDemonName() {
		return demonName;
	}
	
	public final int id() {
		return id;
	}
	
	public ItemStack[] getStacks() {
		return stacks;
	}
	
	public ItemStack getStack(int slot) {
		return slot >= 0 && slot < stacks.length ? stacks[slot] : null;
	}
	
	public static SummoningRecipe[] recipes = new SummoningRecipe[16];
	
	public static final SummoningRecipe HEAVEN_ZOMBIE = new SummoningRecipe(0, EntityHeavenZombie.class, "Heaven Zombie", 
			BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE 
			).setAngel();
	public static final SummoningRecipe HELL_ZOMBIE = new SummoningRecipe(1, EntityHellZombie.class, "Hell Zombie",
			RED, RED, RED, RED, RED, RED, RED, RED, RED			
			);

	public static SummoningRecipe fromDamage(ItemStack stack) {
		return fromDamage(stack.getItemDamage());
	}

	private static SummoningRecipe fromDamage(int damage) {
		return damage < recipes.length && damage >= 0 ? recipes[damage] : null;
	}

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
}