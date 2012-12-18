package demonmodders.crymod1.common.network;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod1.common.karma.PlayerPowersHandler;

public class PacketClientRequest extends CrymodPacket {

	private Action action;
	
	public PacketClientRequest() {}
	
	public PacketClientRequest(Action action) {
		this.action = action;
	}
	
	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeByte(action.ordinal());
	}

	@Override
	void readData(ByteArrayDataInput in) {
		action = Action.values()[in.readByte()];
	}

	@Override
	void execute(EntityPlayer player) {
		switch (action) {
		case INVISIBLE:
			PlayerPowersHandler.instance().onPlayerInvisibilityRequest(player);
			break;
		}
	}
	
	public static enum Action {
		INVISIBLE
	}

}