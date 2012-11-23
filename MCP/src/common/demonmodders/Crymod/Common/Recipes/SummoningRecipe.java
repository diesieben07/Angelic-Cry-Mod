package demonmodders.Crymod.Common.Recipes;

import java.util.List;

import net.minecraft.src.ItemStack;

public class SummoningRecipe {
	private final ItemStack[] stacks;
	private final String demon;
	
	public SummoningRecipe(ItemStack[] stacks, String demon) {
		this.stacks = stacks;
		this.demon = demon;
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
	
	public String getDemon() {
		return demon;
	}
}
