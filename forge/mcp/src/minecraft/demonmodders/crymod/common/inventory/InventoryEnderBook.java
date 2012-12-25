package demonmodders.crymod.common.inventory;

import java.util.ArrayList;
import java.util.List;

import demonmodders.crymod.common.playerinfo.PlayerInfo;
import demonmodders.crymod.common.recipes.SummoningRecipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryEnderBook extends AbstractInventory {

	private final EntityPlayer player;
	
	public InventoryEnderBook(EntityPlayer player) {
		super(true);
		this.player = player;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}

	@Override
	public int getSizeInventory() {
		return 9;
	}

	@Override
	public String getInvName() {
		return "enderBook";
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player == this.player;
	}
	
	public static byte[] getKnownRecipes(EntityPlayer player) {
		return PlayerInfo.getModEntityData(player).getByteArray("enderBook");
	}
	
	public byte[] getKnownRecipes() {
		return getKnownRecipes(player);
	}
}