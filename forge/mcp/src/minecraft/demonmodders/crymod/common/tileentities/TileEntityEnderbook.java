package demonmodders.crymod.common.tileentities;

import java.util.List;

import demonmodders.crymod.common.inventory.InventoryEnderBook;
import demonmodders.crymod.common.items.ItemCryMod;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityEnderbook extends TileEntity {

	@Override
	public void updateEntity() {
		AxisAlignedBB checkBB = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(xCoord - 3, yCoord - 1, zCoord - 3, xCoord + 3, yCoord + 1, zCoord + 3);
		List<EntityItem> itemsNearby = worldObj.getEntitiesWithinAABB(EntityItem.class, checkBB);
		for (EntityItem item : itemsNearby) {
			if (item.func_92014_d().itemID == ItemCryMod.crystal.shiftedIndex) {
				String ownerName = item.getEntityData().getCompoundTag("summoningmod").getString("tossedBy");
				EntityPlayer owner = worldObj.getPlayerEntityByName(ownerName);
				if (owner != null) {
					System.out.println("Tossed by: " + owner.username);
				}
			}
		}
	}
}