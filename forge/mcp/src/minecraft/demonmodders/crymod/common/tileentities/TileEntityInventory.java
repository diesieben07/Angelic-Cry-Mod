package demonmodders.crymod.common.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import demonmodders.crymod.common.inventory.InventoryHelper;

public abstract class TileEntityInventory extends TileEntity implements IInventory {

	public TileEntityInventory() {
		this(true);
	}
	
	public TileEntityInventory(boolean initStorage) {
		if (initStorage) {
			initStorage();
		}
	}
	
	ItemStack[] stacks;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList slotList = nbt.getTagList("slots");
		for (int i = 0; i < slotList.tagCount(); i++) {
			NBTTagCompound slotCompound = (NBTTagCompound)slotList.tagAt(i);
			int slot = slotCompound.getByte("slot");
			stacks[i] = ItemStack.loadItemStackFromNBT(slotCompound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList slotList = new NBTTagList();
		for (byte i = 0; i < stacks.length; i++) {
			if (stacks[i] != null) {
				NBTTagCompound slotCompound = new NBTTagCompound();
				stacks[i].writeToNBT(slotCompound);
				slotCompound.setByte("slot", i);
				slotList.appendTag(slotCompound);
			}
		}
		nbt.setTag("slots", slotList);
	}
	
	final void initStorage() {
		stacks = new ItemStack[getSizeInventory()];
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return stacks[slot];
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
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
	}

	@Override
	public void onInventoryChanged() { }

	@Override
	public void openChest() { }

	@Override
	public void closeChest() { }
}