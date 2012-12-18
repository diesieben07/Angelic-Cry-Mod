package demonmodders.Crymod.Common.Items;

import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemSword;

public abstract class ItemCrymodSword extends ItemSword {

	public ItemCrymodSword(String itemName, int defaultId, EnumToolMaterial toolMaterial) {
		super(defaultId, toolMaterial);
		ItemCryMod.initCrymodItem(this, itemName);
	}
}