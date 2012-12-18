package demonmodders.crymod.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InventoryCrystalBag extends InventoryItemStack {

	public InventoryCrystalBag(ItemStack theStack, EntityPlayer player) {
		super(theStack, player);
		initStorage();
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public String getInvName() {
		return "crystalBag";
	}

}
