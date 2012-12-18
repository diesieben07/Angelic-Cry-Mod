package demonmodders.Crymod.Common.Inventory;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;

public abstract class InventoryItemStack extends AbstractInventory {
	
	private final ItemStack theStack;
	private final ItemStack originalStack;
	private final EntityPlayer player;
		
	public InventoryItemStack(ItemStack theStack, EntityPlayer player) {
		super(false);
		this.theStack = theStack.copy();
		originalStack = theStack;
		this.player = player;
	}
	
	public InventoryItemStack(ItemStack theStack) {
		this(theStack, null);
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.player == null) {
			return true;
		} else if (this.player != player) {
			return false;
		} else {
			return ItemStack.areItemStacksEqual(player.inventory.getCurrentItem(), originalStack);
		}
	}

	@Override
	public void openChest() {
		if (theStack.stackTagCompound == null) {
			theStack.stackTagCompound = new NBTTagCompound();
		}
		
		NBTTagList slotList = theStack.stackTagCompound.getTagList("slots");
		for (int i = 0; i < slotList.tagCount(); i++) {
			NBTTagCompound slotCompound = (NBTTagCompound)slotList.tagAt(i);
			int slot = slotCompound.getInteger("slot");
			stacks[slot] = ItemStack.loadItemStackFromNBT(slotCompound);
		}
	}

	@Override
	public void closeChest() {
		NBTTagList slotList = new NBTTagList();
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i] != null) {
				NBTTagCompound slotCompound = new NBTTagCompound();
				slotCompound.setInteger("slot", i);
				stacks[i].writeToNBT(slotCompound);
				slotList.appendTag(slotCompound);
			}
		}
		theStack.stackTagCompound.setTag("slots", slotList);
		saveItemStack();
	}
	
	void saveItemStack() {
		if (player != null && isUseableByPlayer(player)) {
			player.inventory.setInventorySlotContents(player.inventory.currentItem, theStack);
		}
	}
}