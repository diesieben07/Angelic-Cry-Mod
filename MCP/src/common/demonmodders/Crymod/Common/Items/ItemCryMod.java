package demonmodders.Crymod.Common.Items;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import demonmodders.Crymod.Common.Crymod;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemCryMod extends Item {

	public static Item summoningBook;
	public static Item crystalYellow;
	public static Item crystalRed;
	public static Item crystalLightBlue;
	public static Item crystalPurple;
	
	public ItemCryMod(int itemId) {
		super(itemId);
	}

	@Override
	public String getTextureFile() {
		return Crymod.TEXTURE_FILE;
	}
	
	public static void createItems() {
		summoningBook = new ItemSummoningBook(Crymod.conf.getItem("summoningBookId", 4765).getInt());
		crystalYellow = new ItemCrystalYellow(Crymod.conf.getItem("crystalYellowId", 4767).getInt());
		crystalRed = new ItemCrystalRed(Crymod.conf.getItem("crystalRedId", 4768).getInt());
		crystalLightBlue = new ItemCrystalLightBlue(Crymod.conf.getItem("crystalLightBlueId", 4769).getInt());
		crystalPurple = new ItemCrystalPurple(Crymod.conf.getItem("crystalPurpleId", 4770).getInt());
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.tabMisc;
	}
}
