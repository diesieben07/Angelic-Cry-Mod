package demonmodders.crymod.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import demonmodders.crymod.common.gui.AbstractContainer;

public class PacketGuiButton extends CrymodPacket {

	private int windowId;
	private int buttonId;
	
	public PacketGuiButton(Container container, int buttonId) {
		windowId = container.windowId;
		this.buttonId = buttonId;
	}

	public PacketGuiButton() { }
	
	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeByte(windowId);
		out.writeByte(buttonId);
	}

	@Override
	void readData(ByteArrayDataInput in) {
		windowId = in.readByte();
		buttonId = in.readByte();
	}

	@Override
	void execute(EntityPlayer player) {
		if (player.openContainer instanceof AbstractContainer && player.openContainer.windowId == windowId) {
			((AbstractContainer)player.openContainer).buttonClick(buttonId, Side.SERVER, player);
		}
	}

}
