package demonmodders.crymod.common.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.gui.GuiType;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public class ItemRecipePage extends ItemCryMod {

	public ItemRecipePage(String itemName, int defaultId) {
		super(itemName, defaultId);
		setHasSubtypes(true);
		setMaxStackSize(1);
		setIconIndex(17);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) { 
		SummoningRecipe recipe = SummoningRecipe.fromDamage(stack);
		if (recipe != null) {
			list.add((recipe.isAngel() ? "§5" : "§4") + recipe.getDemonName());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemId, CreativeTabs tab, List list) {
		for (SummoningRecipe recipe : SummoningRecipe.recipes) {
			if (recipe != null) {
				list.add(new ItemStack(this, 1, recipe.id()));
			}
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.openGui(Crymod.instance, GuiType.RECIPE_PAGE.getGuiId(), world, 0, 0, 0);
		return stack;
	}
}