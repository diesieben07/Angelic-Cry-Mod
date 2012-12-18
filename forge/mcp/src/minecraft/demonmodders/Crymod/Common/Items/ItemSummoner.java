package demonmodders.Crymod.Common.Items;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.Gui.GuiType;

public class ItemSummoner extends ItemCryMod {
	
	public ItemSummoner(String itemName, int itemId) {
		super(itemName, itemId);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	@Override
	public boolean getShareTag() {
		// true because we need the tag to store the inventory
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			GuiType guiType = Type.fromItemDamage(itemStack).guiType;
			player.openGui(Crymod.instance, guiType.getGuiId(), world, 0, 0, 0);
		}
		return itemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int damage) {
		return Type.fromItemDamage(damage).icon;
	}

	@Override
	public String getItemNameIS(ItemStack itemStack) {
		return itemStack.getItemDamage() == 0 ? "item.crymod_summoningBook" : "item.crymod_evilTablet";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemId, CreativeTabs creativeTab, List itemList) {
		itemList.add(new ItemStack(this, 1, 0));
		itemList.add(new ItemStack(this, 1, 1));
	}
	
	public static enum Type {
		SUMMONING_BOOK(GuiType.SUMMONING_BOOK, 0), EVIL_TABLET(GuiType.EVIL_TABLET, 1);
		
		public final GuiType guiType;
		public final int icon;
		
		private Type(GuiType guiType, int icon) {
			this.guiType = guiType;
			this.icon = icon;
		}
		
		public static Type fromItemDamage(int damage) {
			return damage == 0 ? SUMMONING_BOOK : EVIL_TABLET;
		}
		
		public static Type fromItemDamage(ItemStack stack) {
			return fromItemDamage(stack.getItemDamage());
		}
		
		public boolean showsAngels() {
			return this == SUMMONING_BOOK;
		}
	}
}