package demonmodders.Crymod.Common.Network;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.HashMap;
import java.util.Map;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.Player;

import static demonmodders.Crymod.Common.Crymod.logger;

import net.minecraft.src.Packet250CustomPayload;

public abstract class CrymodPacket {
	
	private static Map<Integer, Class<? extends CrymodPacket>> packetMappings = new HashMap<Integer, Class<? extends CrymodPacket>>();
	
	static {
		
	}
	
	abstract void writeData(DataOutput out);
	
	abstract void readData(DataInput in);
	
	abstract void execute(Player player);
	
	public static void execute(Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput input = ByteStreams.newDataInput(packet.data);
		int packetId = input.readByte();
		Class<? extends CrymodPacket> packetClass = packetMappings.get(packetId);
		if (packetClass == null) {
			logger.warning("Recieved unknown Packet-Id " + packetId);
		} else {
			try {
				CrymodPacket parsedPacket = packetClass.newInstance();
				parsedPacket.readData(input);
				parsedPacket.execute(player);
			} catch (Exception e) {
				logger.warning("Exception during packet handling: " + e.getClass().getSimpleName() + " (" + e.getMessage() + ")");
			}
		}
	}
}
