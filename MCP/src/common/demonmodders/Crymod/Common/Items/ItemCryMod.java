package demonmodders.Crymod.Common.Items;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.Gui.GuiType;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

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
		summoner = new ItemSummoner(Crymod.conf.getItem("summonerId", 4765).getInt());
		crystal = new ItemCrystal(Crymod.conf.getItem("crystalId", 4766).getInt()).setItemName("crystal");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.tabMisc;
	}
}
