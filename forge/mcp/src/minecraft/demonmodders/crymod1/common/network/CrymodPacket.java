package demonmodders.crymod1.common.network;

import static demonmodders.crymod1.common.Crymod.logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import demonmodders.crymod1.common.Crymod;

public abstract class CrymodPacket {
	
	public static final String CHANNEL = "SummoningMod";
	
	private static Map<Integer, Class<? extends CrymodPacket>> idToClassMap = new HashMap<Integer, Class<? extends CrymodPacket>>();
	private static Map<Class<? extends CrymodPacket>, Integer> classToIdMap = new HashMap<Class<? extends CrymodPacket>, Integer>();
	
	private static void addMapping(int packetId, Class<? extends CrymodPacket> clazz) {
		idToClassMap.put(packetId, clazz);
		classToIdMap.put(clazz, packetId);
	}
	
	static {
		addMapping(0, PacketGuiButton.class);
		addMapping(1, PacketPlayerInfo.class);
		addMapping(2, PacketClientRequest.class);
		addMapping(3, PacketClientEffect.class);
	}
	
	public final Packet generatePacket() {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeByte(classToIdMap.get(getClass()));
		writeData(output);
		byte[] bytes = output.toByteArray();
		if (bytes.length <= Short.MAX_VALUE + 1) {
			return PacketDispatcher.getTinyPacket(Crymod.instance, bytes[0], Arrays.copyOfRange(bytes, 1, bytes.length));
		} else {
			return new Packet250CustomPayload(CHANNEL, bytes);
		}
	}
	
	public final void sendToServer() {
		PacketDispatcher.sendPacketToServer(generatePacket());
	}
	
	public final void sendToPlayer(EntityPlayer player) {
		PacketDispatcher.sendPacketToPlayer(generatePacket(), (Player)player);
	}
	
	public final void sendToAllNear(Entity entity, double radius) {
		FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendToAllNear(entity.posX, entity.posY, entity.posZ, radius, entity.dimension, generatePacket());
	}
	
	abstract void writeData(ByteArrayDataOutput out);
	
	abstract void readData(ByteArrayDataInput in);
	
	abstract void execute(EntityPlayer player);
	
	public static void execute(ByteArrayDataInput input, int packetId, EntityPlayer player) {
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
