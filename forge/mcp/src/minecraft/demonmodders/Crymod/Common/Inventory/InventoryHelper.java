package demonmodders.crymod.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public abstract class InventoryHelper {
	
	public static ItemStack genericStackDecrease(IInventory inventory, int slot, int numDecrease) {
		ItemStack stack = inventory.getStackInSlot(slot);
		
		if (stack != null) {
            ItemStack returnStack;

            if (stack.stackSize <= numDecrease) {
                returnStack = stack;
                inventory.setInventorySlotContents(slot, null);
                return returnStack;
            } else {
                returnStack = stack.splitStack(numDecrease);

                if (stack.stackSize == 0) {
                	inventory.setInventorySlotContents(slot, null);
                }
                
                return returnStack;
            }
        } else {
            return null;
        }
	}
}
