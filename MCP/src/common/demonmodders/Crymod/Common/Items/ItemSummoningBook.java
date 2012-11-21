package demonmodders.Crymod.Common.Items;

import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.Gui.GuiType;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemSummoningBook extends ItemCryMod {

	public ItemSummoningBook(int itemId) {
		super(itemId);
		setIconIndex(0);
		setItemName("summoningBook");
		System.out.println(getLocalItemName(null));
	}

	@Override
	public boolean getShareTag() {
		// true because we need the tag to store the inventory
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			player.openGui(Crymod.instance, GuiType.SUMMONING_BOOK.getGuiId(), world, 0, 0, 0);
		}
		return itemStack;
	}
	
	
		
}