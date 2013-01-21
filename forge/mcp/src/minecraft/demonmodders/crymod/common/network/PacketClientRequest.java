package demonmodders.crymod.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.karma.PlayerPowersHandler;

/**
 * sent by the client to request something, e.g. being invisible
 *
 */
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
		case UPDATE:
			if (isOp(player)) {
				Crymod.updater.startDownload();
			}
			break;
		case UPDATE_RETRY:
			if (isOp(player)) {
				Crymod.updater.startIfNotRunning();
			}
			break;
		}
	}
	
	private boolean isOp(EntityPlayer player) {
		return player instanceof EntityPlayerMP && ((EntityPlayerMP)player).mcServer.getConfigurationManager().getOps().contains(player.username.toLowerCase());
	}
	
	public static enum Action {
		INVISIBLE, UPDATE, UPDATE_RETRY
	}

}