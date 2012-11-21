package demonmodders.Crymod.Common.Inventory;

import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Slot;

public class ContainerSummoningBook extends AbstractContainer {

	public ContainerSummoningBook(InventorySummoningBook inventory, InventoryPlayer inventoryPlayer) {
		super(inventory);
		addSlotToContainer(new Slot(inventory, 0, 80, -12));
		addSlotToContainer(new Slot(inventory, 1, 80, 25));
		addSlotToContainer(new Slot(inventory, 2, 58, 34));
		addSlotToContainer(new Slot(inventory, 3, 103, 34));
		addSlotToContainer(new Slot(inventory, 4, 80, 55));
		addSlotToContainer(new Slot(inventory, 5, 38, 55));
		addSlotToContainer(new Slot(inventory, 6, 121, 55));
		
		addSlotToContainer(new Slot(inventory, 7, 80, 85));
		
		addSlotToContainer(new Slot(inventory, 8, 58, 76));
		
		addSlotToContainer(new Slot(inventory, 9, 103, 76));
		
		
		InventoryHelper.addPlayerInventoryToContainer(this, inventoryPlayer, 8, 129);
	}

}
