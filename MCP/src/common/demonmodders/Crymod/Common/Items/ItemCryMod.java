package demonmodders.Crymod.Common.Items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import demonmodders.Crymod.Common.Crymod;

public class ItemCryMod extends Item {

	public static Item summoner;
	public static Item crystal;
	public static Item recipePage;
	public static Item crystalBag;
	
	public ItemCryMod(int itemId) {
		super(itemId);
	}
	
	public static void createItems() {
		summoner = new ItemSummoner(Crymod.conf.getItem("summonerId", 4765).getInt()).setHasSubtypes(true).setMaxStackSize(1);
		crystal = new ItemCrystal(Crymod.conf.getItem("crystalId", 4766).getInt()).setHasSubtypes(true).setItemName("crystal").setMaxStackSize(1);
		recipePage = new ItemRecipePage(Crymod.conf.getItem("recipePageId", 4767).getInt()).setHasSubtypes(true).setItemName("recipePage").setMaxStackSize(1).setIconIndex(15);
		crystalBag = new ItemCrystalBag(Crymod.conf.getItem("crystalBagId", 4768).getInt()).setHasSubtypes(true).setMaxStackSize(1).setItemName("crystalBag").setIconCoord(12, 2);
	}
	
	@Override
	public String getTextureFile() {
		return Crymod.TEXTURE_FILE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.tabMisc;
	}
	
	public static void createStackCompound(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
	}
}