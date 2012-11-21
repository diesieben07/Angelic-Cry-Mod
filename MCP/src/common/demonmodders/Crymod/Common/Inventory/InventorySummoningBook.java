package demonmodders.Crymod.Common.Inventory;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public class InventorySummoningBook extends InventoryItemStack {

	public InventorySummoningBook(ItemStack theStack, EntityPlayer player) {
		super(theStack, player);
	}

	@Override
	public int getSizeInventory() {
		return 10;
	}

	@Override
	public String getInvName() {
		return "summoningBook";
	}
}
