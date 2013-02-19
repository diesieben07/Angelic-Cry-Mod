package demonmodders.crymod.common.handlers;

import static demonmodders.crymod.common.Crymod.proxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import demonmodders.crymod.common.container.ContainerCrystalBag;
import demonmodders.crymod.common.container.ContainerEnderBook;
import demonmodders.crymod.common.container.ContainerEntityInfo;
import demonmodders.crymod.common.container.ContainerMagiciser;
import demonmodders.crymod.common.container.ContainerQuests;
import demonmodders.crymod.common.container.ContainerRechargeStation;
import demonmodders.crymod.common.container.ContainerRecipePage;
import demonmodders.crymod.common.container.ContainerSummoner;
import demonmodders.crymod.common.container.GuiType;
import demonmodders.crymod.common.entities.EntityQuester;
import demonmodders.crymod.common.entities.EntitySummonable;
import demonmodders.crymod.common.inventory.InventoryCrystalBag;
import demonmodders.crymod.common.inventory.InventoryEnderBook;
import demonmodders.crymod.common.inventory.InventorySummoner;
import demonmodders.crymod.common.recipes.SummoningRecipe;
import demonmodders.crymod.common.tileentities.TileEntityMagiciser;
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
			if (entity != null && entity instanceof EntitySummonable) {
				return new ContainerEntityInfo((EntitySummonable)entity, player);
			} else {
				return null;
			}
		case MAGICISER:
			return new ContainerMagiciser((TileEntityMagiciser)world.getBlockTileEntity(x, y, z), player.inventory);
		case QUESTS:
			entity = world.getEntityByID(x);
			if (entity != null && entity instanceof EntityQuester) {
				return new ContainerQuests((EntityQuester)entity);
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