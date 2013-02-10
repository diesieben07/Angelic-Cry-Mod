package demonmodders.crymod.common.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ScrollingSlot extends SlotNoPickup {

	private int slotIndex;
	private final int idMin;
	private final int idMax;
	
	public ScrollingSlot(IInventory inventory, int idMin, int idMax, int x, int y) {
		super(inventory, idMin, x, y);
		this.idMin = slotIndex = idMin;
		this.idMax = idMax;
	}
	
	public void update() {
		slotIndex++;
		if (slotIndex == idMax) {
			slotIndex = idMin;
		}
	}

	@Override
	public ItemStack getStack() {
		return inventory.getStackInSlot(slotIndex);
	}
}