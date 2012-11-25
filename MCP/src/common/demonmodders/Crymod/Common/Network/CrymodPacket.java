package demonmodders.Crymod.Common.Network;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import static demonmodders.Crymod.Common.Crymod.logger;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Packet250CustomPayload;

public abstract class CrymodPacket {
	
	public static final String CHANNEL = "crymod";
	
	private static Map<Integer, Class<? extends CrymodPacket>> idToClassMap = new HashMap<Integer, Class<? extends CrymodPacket>>();
	private static Map<Class<? extends CrymodPacket>, Integer> classToIdMap = new HashMap<Class<? extends CrymodPacket>, Integer>();
	
	private static void addMapping(int packetId, Class<? extends CrymodPacket> clazz) {
		idToClassMap.put(packetId, clazz);
		classToIdMap.put(clazz, packetId);
	}
	
	static {
		addMapping(0, PacketPageChange.class);
		addMapping(1, PacketPlayerKarma.class);
	}
	
	public final Packet250CustomPayload generatePacket() {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeByte(classToIdMap.get(getClass()));
		try {
			writeData(output);
		} catch (IOException e) {
			logger.warning("Exception during writing of packet data!");
		}
		return new Packet250CustomPayload(CHANNEL, output.toByteArray());
	}
	
	public final void sendToServer() {
		PacketDispatcher.sendPacketToServer(generatePacket());
	}
	
	public final void sentToPlayer(EntityPlayer player) {
		PacketDispatcher.sendPacketToPlayer(generatePacket(), (Player)player);
	}
	
	abstract void writeData(DataOutput out) throws IOException;
	
	abstract void readData(DataInput in) throws IOException;
	
	abstract void execute(EntityPlayer player);
	
	public static void execute(Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput input = ByteStreams.newDataInput(packet.data);
		int packetId = input.readByte();
		Class<? extends CrymodPacket> packetClass = idToClassMap.get(packetId);
		if (packetClass == null) {
			logger.warning("Recieved unknown Packet-Id " + packetId);
		} else {
			try {
				CrymodPacket parsedPacket = packetClass.newInstance();
				parsedPacket.readData(input);
				parsedPacket.execute((EntityPlayer)player);
			} catch (Exception e) {
				logger.warning("Exception during packet handling: " + e.getClass().getSimpleName() + " (" + e.getMessage() + ")");
			}
		}
	}
}
