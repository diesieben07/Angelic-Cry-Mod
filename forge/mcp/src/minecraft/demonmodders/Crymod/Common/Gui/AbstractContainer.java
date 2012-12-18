package demonmodders.Crymod.Common.Gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
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