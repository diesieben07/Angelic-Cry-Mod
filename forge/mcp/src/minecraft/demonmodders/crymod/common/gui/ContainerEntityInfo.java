package demonmodders.crymod.common.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import demonmodders.crymod.common.entities.SummonableBase;
import demonmodders.crymod.common.inventory.SlotArmor;
import demonmodders.crymod.common.inventory.SlotNoPickup;

public class ContainerEntityInfo extends AbstractContainer<SummonableBase> {

	public ContainerEntityInfo(SummonableBase inventory, InventoryPlayer inventoryPlayer) {
		super(inventory);
		
		addSlotToContainer(new SlotArmor(inventory, 4, 160, 13, 0));
		addSlotToContainer(new SlotArmor(inventory, 3, 160, 40, 1));
		addSlotToContainer(new SlotArmor(inventory, 2, 214, 13, 2));
		addSlotToContainer(new SlotArmor(inventory, 1, 214, 40, 3));
		addSlotToContainer(new Slot(inventory, 0, 186, 64));
		
		for (int k = 0; k < 9; k++) {
			addSlotToContainer(new Slot(inventoryPlayer, k, 41 + k * 18, 171));
        }
	}
}