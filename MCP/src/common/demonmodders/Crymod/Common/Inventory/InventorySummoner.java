package demonmodders.Crymod.Common.Inventory;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import demonmodders.Crymod.Common.Items.ItemSummoner;
import demonmodders.Crymod.Common.Recipes.SummoningEntityList;

public class InventorySummoner extends InventoryItemStack {

	private final boolean showAngels;
	
	public InventorySummoner(ItemStack theStack, EntityPlayer player) {
		super(theStack, player);
		showAngels = ItemSummoner.Type.fromItemDamage(theStack).showsAngels();
		initStorage();
	}

	public boolean getShowAngels() {
		return showAngels;
	}
	
	@Override
	public int getSizeInventory() {
		return SummoningEntityList.getNumSummonings(showAngels) * 10;
	}

	@Override
	public String getInvName() {
		return "";
	}
}
