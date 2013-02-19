package demonmodders.crymod.common.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.container.GuiType;
import demonmodders.crymod.common.network.PacketClientAction;
import demonmodders.crymod.common.network.PacketClientEffect;

public class ItemSummoner extends ItemCryMod {
	
	public ItemSummoner(String itemName, int itemId) {
		super(itemName, itemId);
		setHasSubtypes(true);
		setMaxStackSize(1);
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
		for (Type type : Type.values()) {
			itemList.add(new ItemStack(this, 1, type.getItemDamage()));
		}
	}
	
	public static enum Type {
		SUMMONING_BOOK(GuiType.SUMMONING_BOOK, 0, PacketClientEffect.Type.SUMMON_GOOD), EVIL_TABLET(GuiType.EVIL_TABLET, 1, PacketClientEffect.Type.SUMMON_BAD);
		
		private final GuiType guiType;
		private final int icon;
		private final PacketClientEffect.Type effect;
		
		private Type(GuiType guiType, int icon, PacketClientEffect.Type effect) {
			this.guiType = guiType;
			this.icon = icon;
			this.effect = effect;
		}
		
		public static Type fromItemDamage(int damage) {
			return damage == 0 || damage >= values().length ? SUMMONING_BOOK : values()[damage];
		}
		
		public static Type fromItemDamage(ItemStack stack) {
			return fromItemDamage(stack.getItemDamage());
		}
		
		public int getItemDamage() {
			return ordinal();
		}
		
		public PacketClientEffect.Type getEffectType() {
			return effect;
		}

		public ItemStack generateItemStack() {
			return new ItemStack(ItemCryMod.summoner, 1, getItemDamage());
		}
	}
}