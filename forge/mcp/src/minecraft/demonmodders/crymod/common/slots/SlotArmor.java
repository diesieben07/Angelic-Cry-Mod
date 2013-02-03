package demonmodders.crymod.common.slots;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class SlotArmor extends Slot {

	private final int armorType;
	
	public SlotArmor(IInventory inventory, int slotId, int x, int y, int armorType) {
		super(inventory, slotId, x, y);
		this.armorType = armorType;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if (stack == null) {
			return false;
		}
		Item item = stack.getItem();
		if (item instanceof ItemArmor) {
			return ((ItemArmor)item).armorType == armorType;
		} else {
			return item.itemID == Block.pumpkin.blockID || item.itemID == Item.skull.itemID;
		}
	}

}
