package demonmodders.Crymod.Common.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import demonmodders.Crymod.Common.Crymod;

public class ItemCryMod extends Item {

	public static Item summoner;
	
	public static Item crystal;
	
	public ItemCryMod(int itemId) {
		super(itemId);
	}

	@Override
	public String getTextureFile() {
		return Crymod.TEXTURE_FILE;
	}
	
	public static void createItems() {
		summoner = new ItemSummoner(Crymod.conf.getItem("summonerId", 4765).getInt()).setHasSubtypes(true).setMaxStackSize(1);
		crystal = new ItemCrystal(Crymod.conf.getItem("crystalId", 4766).getInt()).setHasSubtypes(true).setItemName("crystal").setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.tabMisc;
	}
}
