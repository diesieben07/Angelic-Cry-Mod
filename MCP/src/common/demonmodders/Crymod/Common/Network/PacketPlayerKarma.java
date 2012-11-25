package demonmodders.Crymod.Common.Network;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteArrayDataInput;

import net.minecraft.src.EntityPlayer;
import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.Karma.PlayerKarma;

public class PacketPlayerKarma extends CrymodPacket {

	private PlayerKarma karma;
	
	public PacketPlayerKarma() { }
	
	public PacketPlayerKarma(PlayerKarma karma) {
		this.karma = karma;
	}
	
	@Override
	void writeData(ByteArrayDataOutput out) {
		karma.write(out);
	}

	@Override
	void readData(ByteArrayDataInput in) {
		karma = PlayerKarma.create(in);
	}

	@Override
	void execute(EntityPlayer player) {
		Crymod.proxy.setClientKarma(karma);
	}

}
