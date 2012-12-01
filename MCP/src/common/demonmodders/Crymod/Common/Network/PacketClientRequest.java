package demonmodders.Crymod.Common.Network;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.Crymod.Common.Karma.PlayerPowersHandler;
import demonmodders.Crymod.Common.PlayerInfo.PlayerInfo;

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