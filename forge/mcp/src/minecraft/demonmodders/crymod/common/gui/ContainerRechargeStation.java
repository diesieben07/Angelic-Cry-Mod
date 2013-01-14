package demonmodders.crymod.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.tileentities.TileEntityRechargeStation;

public class ContainerRechargeStation extends AbstractContainer<TileEntityRechargeStation> {

	public ContainerRechargeStation(TileEntityRechargeStation inventory, InventoryPlayer playerInventory) {
		super(inventory);
		addSlotToContainer(new Slot(inventory, 9, 124, 35));

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                this.addSlotToContainer(new Slot(inventory, y + x * 3, 30 + y * 18, 17 + x * 18));
            }
        }
        
        addPlayerInventoryToContainer(playerInventory, 8, 84);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		Slot slotToTransfer = getSlot(slotId);
		if (slotToTransfer == null || !slotToTransfer.getHasStack()) {
			return null;
		}
		
		ItemStack stackToTransfer = slotToTransfer.getStack();
		ItemStack oldStack = stackToTransfer.copy();
		
		if (slotId < inventory.getSizeInventory()) { // transfer from station to inventory
			if (!mergeItemStack(stackToTransfer, inventory.getSizeInventory(), inventory.getSizeInventory() + 36, true)) {
				return null;
			}
		} else { // transfer from inventory
			if (stackToTransfer.itemID == ItemCryMod.crystal.itemID) { // merge it with one of the crystal slots
				if (!mergeItemStack(stackToTransfer, 1, 10, false)) {
					return null;
				}
			} else if (stackToTransfer.itemID == Item.ingotGold.itemID) { // merge it with the gold slot
				if (!mergeItemStack(stackToTransfer, 0, 1, false)) {
					return null;
				}
			} else if (slotId >= inventory.getSizeInventory() + 27) { // its in the hotbar
				if (!mergeItemStack(stackToTransfer, inventory.getSizeInventory(), inventory.getSizeInventory() + 27, false)) { // merge it with the upper 3 rows
					return null;
				}
			} else { // its in the upper 3 rows of the player inventory
				if (!mergeItemStack(stackToTransfer, inventory.getSizeInventory() + 27, inventory.getSizeInventory() + 36, false)) { // merge it with the hotbar
					return null;
				}
			}
		}
		
		if (stackToTransfer.stackSize == 0) {
			slotToTransfer.putStack(null); // completely transferred
		} else {
			slotToTransfer.onSlotChanged(); // only partially transferred
		}
		
		if (stackToTransfer.stackSize == oldStack.stackSize) {
			return null; // we cannot transfer this stack
		}
		
		return oldStack;
	}
}