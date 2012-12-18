package demonmodders.Crymod.Common.Gui;

import static demonmodders.Crymod.Common.Crymod.proxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import demonmodders.Crymod.Common.Inventory.InventoryCrystalBag;
import demonmodders.Crymod.Common.Inventory.InventorySummoner;
import demonmodders.Crymod.Common.TileEntities.TileEntityRechargeStation;

public class CrymodGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (GuiType.fromGuiId(ID)) {
		case SUMMONING_BOOK:
		case EVIL_TABLET:
			return new ContainerSummoner(new InventorySummoner(player.getCurrentEquippedItem(), player), player.inventory);
		case RECHARGE_STATION:
			return new ContainerRechargeStation((TileEntityRechargeStation)world.getBlockTileEntity(x, y, z), player.inventory);
		case CRYSTAL_BAG:
			return new ContainerCrystalBag(new InventoryCrystalBag(player.getCurrentEquippedItem(), player), player.inventory);
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
