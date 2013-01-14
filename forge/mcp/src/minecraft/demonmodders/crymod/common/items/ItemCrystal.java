package demonmodders.crymod.common.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrystal extends ItemCryMod {

	public static final int MAX_CHARGE = 50;
	
	public ItemCrystal(String itemName, int defaultId) {
		super(itemName, defaultId);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int damage) {
		return CrystalType.fromItemDamage(damage).getIconIndex();
	}

	@Override
	public String getItemNameIS(ItemStack stack) {
		return getItemName() + "." + CrystalType.fromItemDamage(stack).getName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemId, CreativeTabs creativeTab, List itemList) {
		for (CrystalType type : CrystalType.crystalTypes) {
			if (type != null) {
				itemList.add(type.generateItemStack());
			}
		}
	}
	
	public static int getCharge(ItemStack itemStack) {
		if (itemStack.getItem().itemID != crystal.itemID || itemStack.stackTagCompound == null) {
			return 0;
		} else {
			return itemStack.stackTagCompound.getByte("summoningCharge");
		}
	}
	
	public static void setCharge(ItemStack itemStack, int charge) {
		if (itemStack.getItem().itemID == crystal.itemID) {
			createStackCompound(itemStack);
			if (charge > MAX_CHARGE) {
				charge = MAX_CHARGE;
			}
			itemStack.stackTagCompound.setByte("summoningCharge", (byte) charge);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return getCharge(stack) != 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		int charge = getCharge(stack);
		if (charge > 0) {
			list.add("§5Charged " + charge + "/" + MAX_CHARGE);
		}
	}
}
