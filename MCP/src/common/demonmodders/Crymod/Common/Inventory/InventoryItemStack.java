package demonmodders.Crymod.Common.Inventory;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

public abstract class InventoryItemStack implements IInventory {

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	private final ItemStack theStack;
	private final EntityPlayer player;
		
	public InventoryItemStack(ItemStack theStack, EntityPlayer player) {
		this.theStack = theStack;
		this.player = player;
		if (theStack.stackTagCompound == null) {
			theStack.stackTagCompound = new NBTTagCompound();
		}
	}
	
	public InventoryItemStack(ItemStack theStack) {
		this(theStack, null);
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return ItemStack.loadItemStackFromNBT(theStack.stackTagCompound.getCompoundTag("slot" + slot));
	}

	@Override
	public ItemStack decrStackSize(int slot, int numDecrease) {
		ItemStack stack = getStackInSlot(slot);
		
		if (stack != null) {
            ItemStack returnStack;

            if (stack.stackSize <= numDecrease) {
                returnStack = stack;
                setInventorySlotContents(slot, null);
                return returnStack;
            } else {
                returnStack = stack.splitStack(numDecrease);

                if (stack.stackSize == 0) {
                    setInventorySlotContents(slot, null);
                }

                return returnStack;
            }
        } else {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return getStackInSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		NBTTagCompound compound = new NBTTagCompound();
		if (stack != null) {
			stack.writeToNBT(compound);
		}
		theStack.stackTagCompound.setCompoundTag("slot" + slot, compound);
		onInventoryChanged();
	}
	
	@Override
	public void onInventoryChanged() {
		if (player != null) {
			player.inventory.setInventorySlotContents(player.inventory.currentItem, theStack);
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.player == null) {
			return true;
		} else if (this.player != player) {
			return false;
		} else {
			return ItemStack.areItemStacksEqual(player.inventory.getCurrentItem(), theStack);
		}
	}

	@Override
	public void openChest() { }

	@Override
	public void closeChest() { }
}