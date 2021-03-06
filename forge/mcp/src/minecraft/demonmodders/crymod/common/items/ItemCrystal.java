package demonmodders.crymod.common.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import demonmodders.crymod.common.Crymod;

public class ItemCrystal extends ItemCryMod {

	public static final int MAX_CHARGE = 50;
	
	public ItemCrystal(String itemName, int defaultId) {
		super(itemName, defaultId);
		setHasSubtypes(true);
		setMaxStackSize(1);
		setCreativeTab(Crymod.crystalTab);
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
		for (CrystalType type : CrystalType.orderedByTier) {
			if (type != null) {
				itemList.add(type.generateItemStack());
				itemList.add(type.generateChargedItemStack());
			}
		}
	}
	
	public static int getCharge(ItemStack itemStack) {
		if (itemStack.itemID != crystal.itemID || itemStack.stackTagCompound == null) {
			return 0;
		} else {
			return itemStack.stackTagCompound.getByte("summoningCharge");
		}
	}
	
	public static void setCharge(ItemStack itemStack, int charge) {
		if (itemStack.itemID == crystal.itemID) {
			createStackCompound(itemStack);
			if (charge > MAX_CHARGE) {
				charge = MAX_CHARGE;
			}
			if (charge < 0) {
				charge = 0;
			}
			itemStack.stackTagCompound.setByte("summoningCharge", (byte) charge);
		}
	}
	
	public static void decreaseCharge(ItemStack stack, int decrease) {
		if (stack.itemID == crystal.itemID) {
			setCharge(stack, getCharge(stack) - decrease);
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
		StringTranslate translate = StringTranslate.getInstance();
		list.add(translate.translateKeyFormat("crymod.crystal.tier", CrystalType.fromItemDamage(stack).getTier()));
		int charge = getCharge(stack);
		if (charge > 0) {
			list.add("\u00A75" + translate.translateKeyFormat("crymod.crystal.charge", charge, MAX_CHARGE));
		}
	}
}
