package demonmodders.crymod.common.creativetab;

import demonmodders.crymod.common.items.CrystalType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabCrystals extends CreativeTabs {

	public CreativeTabCrystals() {
		super("crymod_crystals");
	}

	@Override
	public ItemStack getIconItemStack() {
		return CrystalType.GOLD.generateItemStack();
	}
}