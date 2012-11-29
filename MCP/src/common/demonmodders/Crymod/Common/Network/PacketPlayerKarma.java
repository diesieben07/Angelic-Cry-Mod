package demonmodders.Crymod.Common.Network;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteArrayDataInput;

import net.minecraft.src.EntityPlayer;
import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.Karma.PlayerKarma;

public class PacketPlayerKarma extends CrymodPacket {

	private float karma;
	
	public PacketPlayerKarma() { }
	
	public PacketPlayerKarma(float karma) {
		this.karma = karma;
	}
	
	public PacketPlayerKarma(PlayerKarma karma) {
		this.karma = karma.getKarma();
	}
	
	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeFloat(karma);
	}

	@Override
	void readData(ByteArrayDataInput in) {
		karma = in.readFloat();
	}

	@Override
	void execute(EntityPlayer player) {
		Crymod.proxy.setClientKarma(karma);
	}

}
