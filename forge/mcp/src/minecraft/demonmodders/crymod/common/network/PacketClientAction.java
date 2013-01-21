package demonmodders.crymod.common.network;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod.common.Crymod;

/**
 * sent by the server to trigger something on the client
 *
 */
public class PacketClientAction extends CrymodPacket {

	private Action action;
	
	public PacketClientAction() { }

	public PacketClientAction(Action action) {
		this.action = action;
	}

	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeByte(getAction().ordinal());
	}

	@Override
	void readData(ByteArrayDataInput in) {
		action = Action.values()[in.readByte()];
	}

	@Override
	void execute(EntityPlayer player) {
		Crymod.proxy.handleClientAction(this);
	}
	
	public Action getAction() {
		return action;
	}

	public static enum Action {
		OPEN_UPDATES
	}
}