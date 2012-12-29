package demonmodders.crymod.common.gui;

import static demonmodders.crymod.common.Crymod.proxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import demonmodders.crymod.common.entities.SummonableBase;
import demonmodders.crymod.common.inventory.InventoryCrystalBag;
import demonmodders.crymod.common.inventory.InventoryEnderBook;
import demonmodders.crymod.common.inventory.InventorySummoner;
import demonmodders.crymod.common.recipes.SummoningRecipe;
import demonmodders.crymod.common.tileentities.TileEntityRechargeStation;

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
		case ENDER_BOOK:
			return new ContainerEnderBook(new InventoryEnderBook(player));
		case RECIPE_PAGE:
			return new ContainerRecipePage(SummoningRecipe.fromDamage(player.getCurrentEquippedItem()));
		case SUMMONED_ENTITY:
			Entity entity = world.getEntityByID(x);
			if (entity != null && entity instanceof SummonableBase) {
				return new ContainerEntityInfo((SummonableBase)entity, player);
			} else {
				return null;
			}
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