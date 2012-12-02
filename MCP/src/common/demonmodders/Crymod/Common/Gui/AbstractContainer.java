package demonmodders.Crymod.Common.Gui;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import cpw.mods.fml.common.Side;

public abstract class AbstractContainer extends Container {

	final IInventory inventory;
	
	public AbstractContainer(IInventory inventory) {
		this.inventory = inventory;
		inventory.openChest();
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		return null;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}

	@Override
	public Slot addSlotToContainer(Slot par1Slot) {
		// make it public, for InventoryHelper
		return super.addSlotToContainer(par1Slot);
	}
	
	@Override
	public void onCraftGuiClosed(EntityPlayer player) {
		super.onCraftGuiClosed(player);
		inventory.closeChest();
	}
	
	public abstract void buttonClick(int buttonId, Side side, EntityPlayer player);
}