package demonmodders.crymod.common.tileentities;

import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.client.FMLClientHandler;

import demonmodders.crymod.common.recipes.MagiciserRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMagiciser extends TileEntityInventory {

	@Override
	public int getSizeInventory() {
		return 7;
	}

	@Override
	public String getInvName() {
		return "crymod_magiciser";
	}

	public void updateMagiciserOutput() {
		if (getStackInSlot(5) == null) { // no crystal
			return;
		}
		List<ItemStack> ingredients = new ArrayList<ItemStack>();
		for (int i = 0; i < 5; i++) {
			ItemStack stack = getStackInSlot(i);
			if (stack != null) {
				ingredients.add(stack);
			}
		}
		if (ingredients.size() < 3) { // at least 3 ingredients
			setInventorySlotContents(6, null);
			return;
		}
		
		float average = 0;
		
		for (ItemStack stack : ingredients) {
			average += (float)MagiciserRecipes.instance().getItemValueFor(stack) / (float)ingredients.size();
		}
		setInventorySlotContents(6, MagiciserRecipes.instance().generateMatch(average));
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		super.setInventorySlotContents(slot, stack);
		if (slot != 6) {
			updateMagiciserOutput();
		}
	}
}