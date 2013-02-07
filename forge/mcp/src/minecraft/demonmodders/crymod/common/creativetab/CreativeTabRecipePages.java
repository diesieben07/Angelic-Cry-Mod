package demonmodders.crymod.common.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import demonmodders.crymod.common.items.ItemCryMod;

public class CreativeTabRecipePages extends CreativeTabs {

	public CreativeTabRecipePages() {
		super("crymod_recipePages");
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(ItemCryMod.recipePage);
	}
}