package demonmodders.crymod.common.gui;

import net.minecraft.entity.player.InventoryPlayer;
import demonmodders.crymod.common.inventory.slots.ScrollingSlot;
import demonmodders.crymod.common.inventory.slots.SlotNoPickup;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public class ContainerRecipePage extends AbstractContainer<SummoningRecipe> {

	private final ScrollingSlot scrollingSlot;
	
	public ContainerRecipePage(SummoningRecipe inventory) {
		super(inventory);
		
		int specialItemCount = inventory.getSizeInventory() - 9;
		
		addSlotToContainer(scrollingSlot = new ScrollingSlot(inventory, 0, specialItemCount, 102, 3));
		
		addSlotToContainer(new SlotNoPickup(inventory, specialItemCount + 0, 54, 34));
		addSlotToContainer(new SlotNoPickup(inventory, specialItemCount + 1, 28, 46));
		addSlotToContainer(new SlotNoPickup(inventory, specialItemCount + 2, 78, 46));
		addSlotToContainer(new SlotNoPickup(inventory, specialItemCount + 3, 17, 69));
		addSlotToContainer(new SlotNoPickup(inventory, specialItemCount + 4, 54, 70));
		addSlotToContainer(new SlotNoPickup(inventory, specialItemCount + 5, 89, 69));
		addSlotToContainer(new SlotNoPickup(inventory, specialItemCount + 6, 29, 89));
		addSlotToContainer(new SlotNoPickup(inventory, specialItemCount + 7, 78, 89));
		addSlotToContainer(new SlotNoPickup(inventory, specialItemCount + 8, 54, 103));
	}

	@Override
	public void clientTick() {
		scrollingSlot.update();
	}
}