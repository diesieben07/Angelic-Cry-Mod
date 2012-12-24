package demonmodders.crymod.common.gui;

import net.minecraft.entity.player.InventoryPlayer;
import demonmodders.crymod.common.inventory.InventoryEnderBook;

public class ContainerEnderBook extends AbstractContainer<InventoryEnderBook> {

	public ContainerEnderBook(InventoryEnderBook inventory, InventoryPlayer inventoryPlayer) {
		super(inventory);
		addPlayerInventoryToContainer(inventoryPlayer, 8, 84);
	}

}
