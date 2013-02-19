package demonmodders.crymod.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import demonmodders.crymod.common.container.AbstractContainer;

public class PacketGuiButton extends CrymodPacket {

	private int windowId;
	private int buttonId;
	
	public PacketGuiButton(int windowId, int buttonId) {
		this.windowId = windowId;
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
		System.out.println(player.openContainer.windowId + " " + windowId);
		if (player.openContainer instanceof AbstractContainer && player.openContainer.windowId == windowId) {
			System.out.println(player.openContainer);
			((AbstractContainer)player.openContainer).buttonClick(buttonId, Side.SERVER, player);
		}
	}

}
