package demonmodders.crymod.common.items;

import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.container.GuiType;
import demonmodders.crymod.common.network.PacketQuestList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemQuestBook extends ItemCryMod {

	public ItemQuestBook(String itemName, int defaultId) {
		super(itemName, defaultId);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		if (!world.isRemote) {
			new PacketQuestList(player).sendToPlayer(player);
		} else {
			Crymod.proxy.openQuestBook();
		}
		return item;
	}
}