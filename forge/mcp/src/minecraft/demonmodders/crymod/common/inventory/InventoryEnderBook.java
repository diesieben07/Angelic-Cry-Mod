package demonmodders.crymod.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryEnderBook extends AbstractInventory {

	private final EntityPlayer player;
	
	public InventoryEnderBook(EntityPlayer player) {
		super(true);
		this.player = player;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public String getInvName() {
		return "enderBook";
	}

	@Override
	public void openChest() {
		readFromNbt(player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag("summoningmod").getCompoundTag("enderbook"));
	}

	@Override
	public void closeChest() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNbt(nbt);
		NBTTagCompound persisted = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		NBTTagCompound summoningMod = persisted.getCompoundTag("summoningmod");
		summoningMod.setCompoundTag("enderbook", nbt);
		persisted.setCompoundTag("summoningmod", summoningMod);
		player.getEntityData().setCompoundTag(EntityPlayer.PERSISTED_NBT_TAG, persisted);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.player == player;
	}
}