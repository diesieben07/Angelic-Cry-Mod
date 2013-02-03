package demonmodders.crymod.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.registry.GameRegistry;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.CrymodUtils;

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
		Crymod.logger.info("Creating items");
		summoner = new ItemSummoner("summoner", 4765);
		crystal = new ItemCrystal("crystal", 4766);
		recipePage = new ItemRecipePage("recipePage", 4767);
		crystalBag = new ItemCrystalBag("crystalBag", 4768);
		
		swordOfDarkness = new ItemSwordOfDarkness("swordOfDarkness", 4769);
	}

	public static void initCrymodItem(Item item, String itemName) {
		item.setTextureFile(CrymodUtils.TEXTURE_FILE);
		item.setCreativeTab(CreativeTabs.tabMisc);
		item.setItemName("crymod_" + itemName);
		GameRegistry.registerItem(item, itemName);
	}
	
	public static void createStackCompound(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
	}
}