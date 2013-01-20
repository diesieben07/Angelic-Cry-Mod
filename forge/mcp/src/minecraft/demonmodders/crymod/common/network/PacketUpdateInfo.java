package demonmodders.crymod.common.network;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class PacketUpdateInfo extends CrymodPacket {

	private String newVersion;
	
	@Override
	void writeData(ByteArrayDataOutput out) {
		
	}

	@Override
	void readData(ByteArrayDataInput in) {
		// TODO Auto-generated method stub

	}

	@Override
	void execute(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

}
