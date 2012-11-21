package demonmodders.Crymod.Common.Gui;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import demonmodders.Crymod.Common.Inventory.ContainerSummoningBook;
import demonmodders.Crymod.Common.Inventory.InventorySummoningBook;
import demonmodders.Crymod.Common.Inventory.AbstractContainer;

import static demonmodders.Crymod.Common.Crymod.proxy;

public class CrymodGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (GuiType.fromGuiId(ID)) {
		case SUMMONING_BOOK:
		case EVIL_TABLET:
			return new ContainerSummoningBook(new InventorySummoningBook(player.getCurrentEquippedItem(), player), player.inventory);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		Container container = (Container) getServerGuiElement(id, player, world, x, y, z);
		return proxy.getClientGuiElement(container, id, player, world, x, y, z);
	}

}
