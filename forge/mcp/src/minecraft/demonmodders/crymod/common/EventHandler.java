package demonmodders.crymod.common;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;

public class EventHandler {
	
	@ForgeSubscribe
	public void onItemToss(ItemTossEvent evt) {
		NBTTagCompound entityData = evt.entityItem.getEntityData();
		NBTTagCompound summoningModData = new NBTTagCompound();
		summoningModData.setString("tossedBy", evt.player.username);
		entityData.setCompoundTag("summoningmod", summoningModData);
	}
}