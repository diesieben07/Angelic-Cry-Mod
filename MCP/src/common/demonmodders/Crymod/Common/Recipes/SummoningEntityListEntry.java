package demonmodders.Crymod.Common.Recipes;

import java.util.List;

import net.minecraft.src.ItemStack;
import demonmodders.Crymod.Common.Entities.SummonableEntity;

public class SummoningEntityListEntry {
	private final ItemStack[] stacks;
	private final Class<? extends SummonableEntity> demon;
	private final String demonName;
	
	public SummoningEntityListEntry(ItemStack[] stacks, Class<? extends SummonableEntity> demon, String demonName) {
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
	
	public Class<? extends SummonableEntity> getDemon() {
		return demon;
	}
	
	public String getDemonName() {
		return demonName;
	}
}
