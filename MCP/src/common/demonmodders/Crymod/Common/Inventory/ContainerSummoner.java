package demonmodders.Crymod.Common.Inventory;

import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Slot;

public class ContainerSummoner extends AbstractContainer {

	public ContainerSummoner(InventorySummoner inventory, InventoryPlayer inventoryPlayer) {
		super(inventory);
		addSlotToContainer(new Slot(inventory, 0, 80, 33));
		addSlotToContainer(new Slot(inventory, 1, 80, 70));
		addSlotToContainer(new Slot(inventory, 2, 58, 79));
		addSlotToContainer(new Slot(inventory, 3, 103, 79));
		addSlotToContainer(new Slot(inventory, 4, 80, 100));
		addSlotToContainer(new Slot(inventory, 5, 38, 100));
		addSlotToContainer(new Slot(inventory, 6, 121, 100));
		
		addSlotToContainer(new Slot(inventory, 7, 80, 130));
		
		addSlotToContainer(new Slot(inventory, 8, 58, 121));
		
		addSlotToContainer(new Slot(inventory, 9, 103, 121));
		
		
		InventoryHelper.addPlayerInventoryToContainer(this, inventoryPlayer, 8, 174);
	}

}
