package demonmodders.crymod.common.tileentities;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.playerinfo.PlayerInfo;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public class TileEntityEnderbook extends TileEntity {

	@Override
	public void updateEntity() {
		AxisAlignedBB checkBB = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(xCoord - 3, yCoord - 1, zCoord - 3, xCoord + 3, yCoord + 1, zCoord + 3);
		List<EntityItem> itemsNearby = worldObj.getEntitiesWithinAABB(EntityItem.class, checkBB);
		for (EntityItem item : itemsNearby) {
			if (item.func_92014_d().itemID == ItemCryMod.recipePage.shiftedIndex) {
				String ownerName = PlayerInfo.getModEntityData(item).getString("tossedBy");
				EntityPlayer owner = worldObj.getPlayerEntityByName(ownerName);
				SummoningRecipe recipe = SummoningRecipe.fromDamage(item.func_92014_d());
				if (owner != null && recipe != null) {
					byte[] knownRecipes = PlayerInfo.getModEntityData(owner).getByteArray("enderBook");
					for (byte recipeId : knownRecipes) {
						if (recipeId == recipe.id()) {
							return;
						}
					}
					item.setDead();
					byte[] newRecipeList = Arrays.copyOf(knownRecipes, knownRecipes.length + 1);
					newRecipeList[knownRecipes.length] = recipe.id();
					PlayerInfo.getModEntityData(owner).setByteArray("enderBook", newRecipeList);
				}
			}
		}
	}
}