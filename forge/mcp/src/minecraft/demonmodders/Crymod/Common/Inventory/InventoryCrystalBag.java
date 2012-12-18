package demonmodders.Crymod.Common.Inventory;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

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
