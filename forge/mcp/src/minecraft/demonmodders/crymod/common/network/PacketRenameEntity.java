package demonmodders.crymod.common.network;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod.common.container.ContainerEntityInfo;

public class PacketRenameEntity extends CrymodPacket {

	private String newName;
	private int windowId;
	
	public PacketRenameEntity(String newName, int windowId) {
		this.newName = newName;
		this.windowId = windowId;
	}

	public PacketRenameEntity() { }

	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeByte(windowId);
		out.writeUTF(newName);
	}

	@Override
	void readData(ByteArrayDataInput in) {
		windowId = in.readByte();
		newName = in.readUTF();
	}

	@Override
	void execute(EntityPlayer player) {
		if (player.openContainer instanceof ContainerEntityInfo && player.openContainer.windowId == windowId) {
			((ContainerEntityInfo)player.openContainer).setNewName(newName);
		}
	}
}