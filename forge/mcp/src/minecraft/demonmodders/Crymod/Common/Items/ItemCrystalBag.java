package demonmodders.Crymod.Common.Items;

import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.Gui.GuiType;

public class ItemCrystalBag extends ItemCryMod {

	public ItemCrystalBag(String itemName, int defaultId) {
		super(itemName, defaultId);
		setHasSubtypes(true);
		setMaxStackSize(1);
		setIconCoord(12, 2);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		player.openGui(Crymod.instance, GuiType.CRYSTAL_BAG.getGuiId(), world, 0, 0, 0);
		return itemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
	}

}
