package demonmodders.crymod.common.inventory.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import demonmodders.crymod.common.tileentities.TileEntityMagiciser;

public class SlotMagiciserResult extends Slot {

	public SlotMagiciserResult(TileEntityMagiciser inventory, int slotId, int x, int y) {
		super(inventory, slotId, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return false;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		super.onPickupFromSlot(player, stack);
		for (int i = 0; i < 5; i++) {
			inventory.setInventorySlotContents(i, null);
		}
	}
}