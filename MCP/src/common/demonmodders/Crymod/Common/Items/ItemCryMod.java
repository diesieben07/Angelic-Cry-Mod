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
	
	public static Item swordOfDarkness;
	
	public ItemCryMod(String itemName, int defaultId) {
		super(Crymod.conf.getItem(itemName + "Id", defaultId).getInt());
		initCrymodItem(this, itemName);
	}
	
	public static void createItems() {
		summoner = new ItemSummoner("summoner", 4765);
		crystal = new ItemCrystal("crystal", 4766);
		recipePage = new ItemRecipePage("recipePage", 4767);
		crystalBag = new ItemCrystalBag("crystalBag", 4768);
		
		swordOfDarkness = new ItemSwordOfDarkness("swordOfDarkness", 4769);
	}

	public static void initCrymodItem(Item item, String itemName) {
		item.setTextureFile(Crymod.TEXTURE_FILE);
		item.setCreativeTab(CreativeTabs.tabMisc);
		item.setItemName("crymod_" + itemName);
	}
	
	public static void createStackCompound(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
	}
}