package demonmodders.Crymod.Common.Gui;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import cpw.mods.fml.common.Side;

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
	
	final void addPlayerInventoryToContainer(IInventory inventoryPlayer, int xStart, int yStart) {
		for (int j = 0; j < 3; j++) {
            for (int i1 = 0; i1 < 9; i1++) {
                addSlotToContainer(new Slot(inventoryPlayer, i1 + j * 9 + 9, xStart + i1 * 18, yStart + j * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            addSlotToContainer(new Slot(inventoryPlayer, k, xStart + k * 18, yStart + 58));
        }
	}
	
	public void buttonClick(int buttonId, Side side, EntityPlayer player) { }
}