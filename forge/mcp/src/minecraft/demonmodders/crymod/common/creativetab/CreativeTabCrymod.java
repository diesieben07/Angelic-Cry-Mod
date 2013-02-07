package demonmodders.crymod.common.creativetab;

import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.items.ItemSummoner;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabCrymod extends CreativeTabs {

	public CreativeTabCrymod() {
		super("crymod_main");
	}

	@Override
	public ItemStack getIconItemStack() {
		return ItemSummoner.Type.SUMMONING_BOOK.generateItemStack();
	}
}