package demonmodders.Crymod.Common.Gui;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import cpw.mods.fml.common.Side;
import demonmodders.Crymod.Common.Inventory.SlotNoPickup;

public abstract class AbstractContainer<T extends IInventory> extends Container {

	final T inventory;
	
	public AbstractContainer(T inventory) {
		this.inventory = inventory;
		inventory.openChest();
	}
	
	public final T getInventoryInstance() {
		return inventory;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		return null;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}
	
	@Override
	public void onCraftGuiClosed(EntityPlayer player) {
		super.onCraftGuiClosed(player);
		inventory.closeChest();
	}
	
	final void addPlayerInventoryToContainer(InventoryPlayer inventoryPlayer, int xStart, int yStart) {
		addPlayerInventoryToContainer(inventoryPlayer, xStart, yStart, false);
	}
	
	final void addPlayerInventoryToContainer(InventoryPlayer inventoryPlayer, int xStart, int yStart, boolean useCantPickup) {
		for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 9; i++) {
                if (useCantPickup && i + j * 9 == inventoryPlayer.currentItem) {
                	addSlotToContainer(new SlotNoPickup(inventoryPlayer, i + j * 9 + 9, xStart + i * 18, yStart + j * 18));
                } else {
                	addSlotToContainer(new Slot(inventoryPlayer, i + j * 9 + 9, xStart + i * 18, yStart + j * 18));
                }
            }
        }

        for (int k = 0; k < 9; k++) {
        	if (useCantPickup && k == inventoryPlayer.currentItem) {
        		addSlotToContainer(new SlotNoPickup(inventoryPlayer, k, xStart + k * 18, yStart + 58));
        	} else {
        		addSlotToContainer(new Slot(inventoryPlayer, k, xStart + k * 18, yStart + 58));
        	}
        }
	}
	
	public void buttonClick(int buttonId, Side side, EntityPlayer player) { }
}