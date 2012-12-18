package demonmodders.crymod.common.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import demonmodders.crymod.common.tileentities.TileEntityRechargeStation;

public class ContainerRechargeStation extends AbstractContainer<TileEntityRechargeStation> {

	public ContainerRechargeStation(TileEntityRechargeStation inventory, InventoryPlayer playerInventory) {
		super(inventory);
		addSlotToContainer(new Slot(inventory, 9, 124, 35));

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                this.addSlotToContainer(new Slot(inventory, y + x * 3, 30 + y * 18, 17 + x * 18));
            }
        }
        
        addPlayerInventoryToContainer(playerInventory, 8, 84);
	}
}
