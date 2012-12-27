package demonmodders.crymod.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class InventoryItemStack extends AbstractInventory {
	
	private ItemStack theStack;
	private ItemStack originalStack;
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
		
		readFromNbt(theStack.stackTagCompound);
	}

	@Override
	public void closeChest() {
		writeToNbt(theStack.stackTagCompound);
		saveItemStack();
	}
	
	void saveItemStack() {
		if (player != null && isUseableByPlayer(player)) {
			player.inventory.setInventorySlotContents(player.inventory.currentItem, theStack);
			originalStack = theStack;
			theStack = theStack.copy();
		}
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		writeToNbt(theStack.stackTagCompound);
		saveItemStack();
	}
}