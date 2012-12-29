package demonmodders.crymod.common;

import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.playerinfo.PlayerInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;

public class EventHandler {
	
	@ForgeSubscribe
	public void onItemToss(ItemTossEvent evt) {
		if (evt.entityItem.func_92014_d().itemID == ItemCryMod.recipePage.shiftedIndex) {
			PlayerInfo.getModEntityData(evt.entityItem).setString("tossedBy", evt.player.username);
		}
	}
}