package demonmodders.crymod.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import demonmodders.crymod.common.inventory.InventoryCrystalBag;
import demonmodders.crymod.common.inventory.slots.SlotForItem;
import demonmodders.crymod.common.items.ItemCryMod;

public class ContainerCrystalBag extends AbstractContainer<InventoryCrystalBag> {

	public ContainerCrystalBag(InventoryCrystalBag inventory, InventoryPlayer inventoryPlayer) {
		super(inventory);
		
		for (int x = 0; x < 3; x++)	{
			for (int y = 0; y < 3; y++) {
				addSlotToContainer(new SlotForItem(inventory, y + x * 3, 62 + y * 18, 17 + x * 18, ItemCryMod.crystal.itemID));
			}
		}
		
		addPlayerInventoryToContainer(inventoryPlayer, 8, 84, true);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		Slot slotToTransfer = getSlot(slotId);
		if (slotToTransfer == null || !slotToTransfer.getHasStack()) {
			return null;
		}
		
		ItemStack stackToTransfer = slotToTransfer.getStack();
		ItemStack oldStack = stackToTransfer.copy();
		
		if (slotId < inventory.getSizeInventory()) { // transfer from crystal bag to inventory
			if (!mergeItemStack(stackToTransfer, inventory.getSizeInventory(), inventory.getSizeInventory() + 36, true)) {
				return null;
			}
		} else { // transfer from inventory
			if (stackToTransfer.itemID == ItemCryMod.crystal.itemID) { // its a crystal
				if (!mergeItemStack(stackToTransfer, 0, inventory.getSizeInventory(), false)) { // merge into the crystal bag
					return null;
				}
			} else { // its some other item
				if (slotId >= inventory.getSizeInventory() + 27) { // its in the hotbar
					if (!mergeItemStack(stackToTransfer, inventory.getSizeInventory(), inventory.getSizeInventory() + 27, false)) { // merge it with the upper 3 rows
						return null;
					}
				} else { // its in the upper 3 rows of the player inventory
					if (!mergeItemStack(stackToTransfer, inventory.getSizeInventory() + 27, inventory.getSizeInventory() + 36, false)) { // merge it with the hotbar
						return null;
					}
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
