package demonmodders.Crymod.Common.Items;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.Gui.GuiType;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemSummoner extends ItemCryMod {
	
	private static final int ICON_SUMMONING_BOOK = 0;
	private static final int ICON_EVIL_TABLET = 1;
	
	public ItemSummoner(int itemId) {
		super(itemId);
		this.setMaxStackSize(1);
	}

	@Override
	public boolean getShareTag() {
		// true because we need the tag to store the inventory
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			GuiType guiType = itemStack.getItemDamage() == 0 ? GuiType.SUMMONING_BOOK : GuiType.EVIL_TABLET;
			player.openGui(Crymod.instance, guiType.getGuiId(), world, 0, 0, 0);
		}
		return itemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int damage) {
		return damage == 0 ? ICON_SUMMONING_BOOK : ICON_EVIL_TABLET;
	}

	@Override
	public String getItemNameIS(ItemStack itemStack) {
		return itemStack.getItemDamage() == 0 ? "item.summoningBook" : "item.evilTablet";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemId, CreativeTabs creativeTab, List itemList) {
		itemList.add(new ItemStack(this, 1, 0));
		itemList.add(new ItemStack(this, 1, 1));
	}
	
	
		
}