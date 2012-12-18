package demonmodders.crymod.common.network;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.karma.PlayerKarma;
import demonmodders.crymod.common.playerinfo.PlayerInfo;

public class PacketPlayerInfo extends CrymodPacket {

	private float karma;
	private int flyTime;
	
	public PacketPlayerInfo() { }
	
	public PacketPlayerInfo(PlayerInfo info) {
		karma = info.getKarma().getKarma();
		flyTime = info.getFlyTime();
	}
	
	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeFloat(karma);
		out.writeByte(flyTime);
	}

	@Override
	void readData(ByteArrayDataInput in) {
		karma = in.readFloat();
		flyTime = in.readByte();
	}

	@Override
	void execute(EntityPlayer player) {
		Crymod.proxy.setClientPlayerInfo(new PlayerInfo(new PlayerKarma(karma), flyTime));
	}

}
