package demonmodders.crymod.common.inventory.slots;

import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.items.ItemCrystal;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotChargedCrystal extends Slot {

	public SlotChargedCrystal(IInventory inventory, int slotIndex, int x, int y) {
		super(inventory, slotIndex, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		return item != null && ItemCrystal.getCharge(item) != 0;
	}
}