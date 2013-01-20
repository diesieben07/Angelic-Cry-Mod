package demonmodders.crymod.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.gui.ContainerEnderBook;

public class PacketEnderBookRecipe extends CrymodPacket {

	public int recipeId;
	public int windowId;
	
	
	public PacketEnderBookRecipe() { }
	
	public PacketEnderBookRecipe(int recipeId, int windowId) {
		this.recipeId = recipeId;
		this.windowId = windowId;
	}
	
	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeByte(windowId);
		out.writeByte(recipeId);
	}

	@Override
	void readData(ByteArrayDataInput in) {
		windowId = in.readByte();
		recipeId = in.readByte();
	}

	@Override
	void execute(EntityPlayer player) {
		if (!(player instanceof EntityPlayerMP) && player.openContainer.windowId == windowId && player.openContainer instanceof ContainerEnderBook) {
			((ContainerEnderBook)player.openContainer).setRecipe(recipeId);
			Crymod.proxy.handleEnderBookRecipe(this);
		}
	}

}
