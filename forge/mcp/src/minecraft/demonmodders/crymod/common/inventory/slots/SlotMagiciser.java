package demonmodders.crymod.common.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotMagiciser extends Slot {

	public SlotMagiciser(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}