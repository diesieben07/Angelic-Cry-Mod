package demonmodders.Crymod.Common.Items;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class ItemCrystal extends ItemCryMod {

	public ItemCrystal(int itemId) {
		super(itemId);
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
	
	
	

}
