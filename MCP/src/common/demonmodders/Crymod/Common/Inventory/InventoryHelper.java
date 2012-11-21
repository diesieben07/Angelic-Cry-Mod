package demonmodders.Crymod.Common.Inventory;

import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;

public abstract class InventoryHelper {
	public static void addPlayerInventoryToContainer(AbstractContainer container, IInventory inventoryPlayer, int xStart, int yStart) {
		for (int j = 0; j < 3; j++)
        {
            for (int i1 = 0; i1 < 9; i1++)
            {
                container.addSlotToContainer(new Slot(inventoryPlayer, i1 + j * 9 + 9, xStart + i1 * 18, yStart + j * 18));
            }
        }

        for (int k = 0; k < 9; k++)
        {
            container.addSlotToContainer(new Slot(inventoryPlayer, k, xStart + k * 18, yStart + 58));
        }
	}
}
