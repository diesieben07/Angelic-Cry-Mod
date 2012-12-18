package demonmodders.crymod.common.items;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public abstract class ItemCrymodSword extends ItemSword {

	public ItemCrymodSword(String itemName, int defaultId, EnumToolMaterial toolMaterial) {
		super(defaultId, toolMaterial);
		ItemCryMod.initCrymodItem(this, itemName);
	}
}