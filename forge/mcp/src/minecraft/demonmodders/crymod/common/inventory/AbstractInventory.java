package demonmodders.crymod.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class AbstractInventory implements IInventory {

	public AbstractInventory() {
		this(false);
	}
	
	public AbstractInventory(boolean initStorage) {
		if (initStorage) {
			initStorage();
		}
	}
	
	protected ItemStack[] stacks;
	
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
	
	final void readFromNbt(NBTTagCompound nbt) {
		NBTTagList slotList = nbt.getTagList("slots");
		for (int i = 0; i < slotList.tagCount(); i++) {
			NBTTagCompound slotCompound = (NBTTagCompound)slotList.tagAt(i);
			int slot = slotCompound.getInteger("slot");
			stacks[slot] = ItemStack.loadItemStackFromNBT(slotCompound);
		}
	}
	
	final void writeToNbt(NBTTagCompound nbt) {
		NBTTagList slotList = new NBTTagList();
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i] != null) {
				NBTTagCompound slotCompound = new NBTTagCompound();
				slotCompound.setInteger("slot", i);
				stacks[i].writeToNBT(slotCompound);
				slotList.appendTag(slotCompound);
			}
		}
		nbt.setTag("slots", slotList);
	}
}