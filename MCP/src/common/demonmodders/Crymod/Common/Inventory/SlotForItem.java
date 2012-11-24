package demonmodders.Crymod.Common.Inventory;

import java.util.Arrays;
import java.util.List;

import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotForItem extends Slot {

	private List<Integer> itemIds = null;
	private List<ItemStack> itemStacks = null;
	
	public SlotForItem(IInventory inventory, int slotIndex, int x, int y, Integer... items) {
		super(inventory, slotIndex, x, y);
		itemIds = Arrays.<Integer>asList(items);
	}

	public SlotForItem(IInventory inventory, int slotIndex, int x, int y, ItemStack... items) {
		super(inventory, slotIndex, x, y);
		itemStacks = Arrays.<ItemStack>asList(items);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if (itemIds != null) {
			return itemIds.contains(stack.itemID);
		} else if (itemStacks != null) {
			for (ItemStack stackCheck : itemStacks) {
				if (ItemStack.areItemStacksEqual(stackCheck, stack)) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}
}
