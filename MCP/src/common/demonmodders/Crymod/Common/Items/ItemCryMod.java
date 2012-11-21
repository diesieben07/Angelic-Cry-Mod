package demonmodders.Crymod.Common.Items;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import demonmodders.Crymod.Common.Crymod;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemCryMod extends Item {

	public static Item summoningBook;
	
	public ItemCryMod(int itemId) {
		super(itemId);
	}

	@Override
	public String getTextureFile() {
		return Crymod.TEXTURE_FILE;
	}
	
	public static void createItems() {
		summoningBook = new ItemSummoningBook(Crymod.conf.getItem("summoningBookId", 4765).getInt());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.tabMisc;
	}
}
