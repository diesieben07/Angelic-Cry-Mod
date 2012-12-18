package demonmodders.crymod.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import demonmodders.crymod.common.items.ItemSummoner;
import demonmodders.crymod.common.recipes.SummoningEntityList;

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
