package demonmodders.Crymod.Common.Network;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.PlayerKarma;

import net.minecraft.src.EntityPlayer;

public class PacketPlayerKarma extends CrymodPacket {

	private PlayerKarma karma;
	
	public PacketPlayerKarma() { }
	
	public PacketPlayerKarma(PlayerKarma karma) {
		this.karma = karma;
	}
	
	@Override
	void writeData(DataOutput out) throws IOException {
		karma.write(out);
	}

	@Override
	void readData(DataInput in) throws IOException {
		karma = PlayerKarma.create(in);
	}

	@Override
	void execute(EntityPlayer player) {
		Crymod.proxy.setClientKarma(karma);
	}

}
