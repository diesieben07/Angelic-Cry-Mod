package demonmodders.crymod.common.inventory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class AbstractInventory implements IInventory, Iterable<ItemStack> {

	private Collection<InventoryChangeListener> listeners = new HashSet<InventoryChangeListener>();
	
	protected ItemStack[] stacks;
	
	public AbstractInventory() {
		this(false);
	}
	
	public AbstractInventory(boolean initStorage) {
		if (initStorage) {
			initStorage();
		}
	}
	
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
	public void onInventoryChanged() {
		for (InventoryChangeListener listener : listeners) {
			listener.onInventoryChange();
		}
	}

	@Override
	public void openChest() { }

	@Override
	public void closeChest() { }
	
	public Iterator<ItemStack> iterator() {
		return new Iterator<ItemStack>() {

			int element = 0;
			
			@Override
			public boolean hasNext() {
				return element < getSizeInventory();
			}

			@Override
			public ItemStack next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				} else {
					return getStackInSlot(element++); 
				}
			}

			@Override
			public void remove() {
				if (element == 0) {
					throw new IllegalStateException();
				} else {
					setInventorySlotContents(element - 1, null);
				}
			}
		};
	}
	
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
	
	public final void registerListener(InventoryChangeListener listener) {
		listeners.add(listener);
	}
	
	public static interface InventoryChangeListener {
		public void onInventoryChange();
	}
}