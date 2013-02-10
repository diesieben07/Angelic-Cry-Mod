package demonmodders.crymod.common.inventory.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ScrollingSlot extends SlotNoPickup {

	private final int idMin;
	private final int idMax;
	
	public ScrollingSlot(IInventory inventory, int idMin, int idMax, int x, int y) {
		super(inventory, idMin, x, y);
		this.idMin = idMin;
		this.idMax = idMax;
	}

	public void tick() {
		slotNumber++;
	}
}