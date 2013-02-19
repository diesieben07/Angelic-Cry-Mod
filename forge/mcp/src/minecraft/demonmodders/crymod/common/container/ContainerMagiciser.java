package demonmodders.crymod.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import demonmodders.crymod.common.inventory.slots.SlotForItem;
import demonmodders.crymod.common.inventory.slots.SlotMagiciser;
import demonmodders.crymod.common.inventory.slots.SlotMagiciserResult;
import demonmodders.crymod.common.items.CrystalType;
import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.tileentities.TileEntityMagiciser;

public class ContainerMagiciser extends AbstractContainer<TileEntityMagiciser> {

	public ContainerMagiciser(TileEntityMagiciser inventory, InventoryPlayer inventoryPlayer) {
		super(inventory);
		
		for (int i = 0; i < 5; i++) {
			addSlotToContainer(new SlotMagiciser(inventory, i, 47 + 18 * i, 13));
		}
		
		addSlotToContainer(new SlotForItem(inventory, 5, 8, 60, CrystalType.MAGIC.generateItemStack()));
		
		addSlotToContainer(new SlotMagiciserResult(inventory, 6, 84, 49));
		
		addPlayerInventoryToContainer(inventoryPlayer, 8, 84);
		
		inventory.updateMagiciserOutput();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		Slot slotToTransfer = getSlot(slotId);
		if (slotToTransfer == null || !slotToTransfer.getHasStack()) {
			return null;
		}
		
		ItemStack stackToTransfer = slotToTransfer.getStack();
		ItemStack oldStack = stackToTransfer.copy();
		
		if (slotId < inventory.getSizeInventory()) { // transfer from magiciser to inventory
			if (!mergeItemStack(stackToTransfer, inventory.getSizeInventory(), inventory.getSizeInventory() + 36, true)) {
				return null;
			}
		} else { // transfer from inventory
			if (CrystalType.MAGIC.containsThis(stackToTransfer)) { // place it in the crystal slot
				if (!mergeItemStack(stackToTransfer, 5, 6, false)) {
					return null;
				}
			} else if (!mergeItemStack(stackToTransfer, 0, 5, false)) { // place it in the topmost 5 slots
				return null;
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