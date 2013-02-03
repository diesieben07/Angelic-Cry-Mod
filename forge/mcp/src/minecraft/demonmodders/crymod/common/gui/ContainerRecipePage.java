package demonmodders.crymod.common.gui;

import net.minecraft.entity.player.InventoryPlayer;
import demonmodders.crymod.common.recipes.SummoningRecipe;
import demonmodders.crymod.common.slots.SlotNoPickup;

public class ContainerRecipePage extends AbstractContainer<SummoningRecipe> {

	public ContainerRecipePage(SummoningRecipe inventory) {
		super(inventory);
		
		addSlotToContainer(new SlotNoPickup(inventory, 0, 54, 34));
		addSlotToContainer(new SlotNoPickup(inventory, 1, 28, 46));
		addSlotToContainer(new SlotNoPickup(inventory, 2, 78, 46));
		addSlotToContainer(new SlotNoPickup(inventory, 3, 17, 69));
		addSlotToContainer(new SlotNoPickup(inventory, 4, 54, 70));
		addSlotToContainer(new SlotNoPickup(inventory, 5, 89, 69));
		addSlotToContainer(new SlotNoPickup(inventory, 6, 29, 89));
		addSlotToContainer(new SlotNoPickup(inventory, 7, 78, 89));
		addSlotToContainer(new SlotNoPickup(inventory, 8, 54, 103));
	}
}