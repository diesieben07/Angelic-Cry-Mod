package demonmodders.crymod.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotNoPlace extends Slot {

	public SlotNoPlace(IInventory inventory, int slotId, int x, int y) {
		super(inventory, slotId, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return false;
	}
}