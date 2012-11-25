package demonmodders.Crymod.Common.Network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import demonmodders.Crymod.Common.Inventory.ContainerSummoner;

public class PacketPageChange extends CrymodPacket {

	private int page;
	private int windowId;
	
	public PacketPageChange() {}
	
	public PacketPageChange(int page, Container container) {
		this.page = page;
		this.windowId = container.windowId;
	}
	
	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeByte(windowId);
		out.writeByte(page);
	}

	@Override
	void readData(ByteArrayDataInput in) {
		windowId = in.readByte();
		page = in.readByte();
	}

	@Override
	void execute(EntityPlayer player) {
		if (player.openContainer.windowId == windowId && player.openContainer instanceof ContainerSummoner) {
			((ContainerSummoner)player.openContainer).setCurrentPage(page);
		}
	}

}
