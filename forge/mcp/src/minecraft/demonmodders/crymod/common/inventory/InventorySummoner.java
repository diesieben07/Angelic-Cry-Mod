package demonmodders.crymod.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import demonmodders.crymod.common.gui.ContainerSummoner;
import demonmodders.crymod.common.items.ItemSummoner;

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
		return ContainerSummoner.NUM_PAGES * 10;
	}

	@Override
	public String getInvName() {
		return "summoners";
	}
}
