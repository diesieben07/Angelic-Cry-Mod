package demonmodders.crymod.common.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import demonmodders.crymod.common.entities.SummonableBase;

public class SummoningEntityListEntry {
	private final ItemStack[] stacks;
	private final Class<? extends SummonableBase> demon;
	private final String demonName;
	
	public SummoningEntityListEntry(ItemStack[] stacks, Class<? extends SummonableBase> demon, String demonName) {
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
	
	public Class<? extends SummonableBase> getDemon() {
		return demon;
	}
	
	public String getDemonName() {
		return demonName;
	}
}
