package demonmodders.Crymod.Common.Inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public abstract class AbstractInventory implements IInventory {

	public AbstractInventory() {
		this(false);
	}
	
	public AbstractInventory(boolean initStorage) {
		if (initStorage) {
			initStorage();
		}
	}
	
	ItemStack[] stacks;
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return stacks[slot];
	}
	
	final void initStorage() {
		stacks = new ItemStack[getSizeInventory()];
	}

	@Override
	public ItemStack decrStackSize(int slot, int numDecrease) {
		return InventoryHelper.genericStackDecrease(this, slot, numDecrease);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = stacks[slot];
		setInventorySlotContents(slot, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		stacks[slot] = stack;
		onInventoryChanged();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() { }

	@Override
	public void openChest() { }

	@Override
	public void closeChest() { }
}