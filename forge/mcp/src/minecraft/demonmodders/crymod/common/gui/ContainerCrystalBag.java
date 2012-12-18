package demonmodders.crymod.common.gui;

import net.minecraft.entity.player.InventoryPlayer;
import demonmodders.crymod.common.inventory.InventoryCrystalBag;
import demonmodders.crymod.common.inventory.SlotForItem;
import demonmodders.crymod.common.items.ItemCryMod;

public class ContainerCrystalBag extends AbstractContainer<InventoryCrystalBag> {

	public ContainerCrystalBag(InventoryCrystalBag inventory, InventoryPlayer inventoryPlayer) {
		super(inventory);
		
		for (int x = 0; x < 3; x++)	{
			for (int y = 0; y < 3; y++) {
				addSlotToContainer(new SlotForItem(inventory, y + x * 3, 62 + y * 18, 17 + x * 18, ItemCryMod.crystal.shiftedIndex));
			}
		}
		
		addPlayerInventoryToContainer(inventoryPlayer, 8, 84, true);
	}

}
