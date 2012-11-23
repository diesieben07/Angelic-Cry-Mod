package demonmodders.Crymod.Common.Inventory;

import demonmodders.Crymod.Common.Recipes.SummoningRecipeRegistry;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public class InventorySummoner extends InventoryItemStack {

	public InventorySummoner(ItemStack theStack, EntityPlayer player) {
		super(theStack, player);
	}

	@Override
	public int getSizeInventory() {
		return SummoningRecipeRegistry.getNumRecipes() * 10;
	}

	@Override
	public String getInvName() {
		return "";
	}
}
