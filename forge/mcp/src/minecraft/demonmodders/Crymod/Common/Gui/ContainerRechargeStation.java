package demonmodders.Crymod.Common.Gui;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotCrafting;
import cpw.mods.fml.common.Side;
import demonmodders.Crymod.Common.TileEntities.TileEntityRechargeStation;

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
