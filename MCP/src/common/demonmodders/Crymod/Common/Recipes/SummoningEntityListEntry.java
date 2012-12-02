package demonmodders.Crymod.Common.Recipes;

import java.util.List;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;

public class SummoningEntityListEntry {
	private final ItemStack[] stacks;
	private final Class<? extends EntityLiving> demon;
	private final String demonName;
	
	public SummoningEntityListEntry(ItemStack[] stacks, Class<? extends EntityLiving> demon, String demonName) {
		this.stacks = stacks;
		this.demon = demon;
		this.demonName = demonName;
	}
	
	public boolean matches(List<ItemStack> checkStacks) {
		if (checkStacks.size() != stacks.length) {
			return false;
		} else {
			for (int i = 0; i < stacks.length; i++) {
				if (!ItemStack.areItemStacksEqual(stacks[i], checkStacks.get(i))) {
					return false;
				}
			}
			return true;
		}
	}
	
	public Class<? extends EntityLiving> getDemon() {
		return demon;
	}
	
	public String getDemonName() {
		return demonName;
	}
}
