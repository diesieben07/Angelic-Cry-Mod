package demonmodders.crymod.common.network;

import static demonmodders.crymod.common.Crymod.logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import demonmodders.crymod.common.Crymod;

public abstract class CrymodPacket {
	
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
		addMapping(4, PacketHealthUpdate.class);
		addMapping(5, PacketRenameEntity.class);
		addMapping(6, PacketNameUpdate.class);
		addMapping(7, PacketUpdateInformation.class);
		addMapping(8, PacketClientAction.class);
	}
	
	public final Packet generatePacket() {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		if (!classToIdMap.containsKey(getClass())) {
			logger.warning("Packet " + getClass() + " is missing a Mapping!");
			return null;
		}
		
		int packetId = classToIdMap.get(getClass());
		
		writeData(output);
		return PacketDispatcher.getTinyPacket(Crymod.instance, (short)packetId, output.toByteArray());
	}
	
	public final void sendToServer() {
		PacketDispatcher.sendPacketToServer(generatePacket());
	}
	
	public final void sendToPlayer(EntityPlayer player) {
		PacketDispatcher.sendPacketToPlayer(generatePacket(), (Player)player);
	}
	
	public final void sendToAllNear(Entity entity, double radius) {
		sendToAllNear(entity.posX, entity.posY, entity.posZ, entity.dimension, radius);
	}
	
	public final void sendToAllNear(TileEntity tileEntity, double radius) {
		sendToAllNear(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity.worldObj.provider.dimensionId, radius);
	}
	
	public final void sendToAllNear(double x, double y, double z, int dimension, double radius) {
		MinecraftServer.getServer().getConfigurationManager().sendToAllNear(x, y, z, radius, dimension, generatePacket());
	}
	
	public final void sendToAllTracking(Entity entity) {
		if (entity.worldObj instanceof WorldServer) {
			((WorldServer)entity.worldObj).getEntityTracker().sendPacketToAllPlayersTrackingEntity(entity, generatePacket());
		}
	}
	
	public final void sendToAll() {
		MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(generatePacket());
	}
	
	public final void sendToOps() {
		MinecraftServer server = MinecraftServer.getServer();
		if (server == null) {
			return;
		}
		ServerConfigurationManager manager = server.getConfigurationManager();
		if (manager == null) {
			return;
		}
		Packet packet = generatePacket();
		Set<String> ops = manager.getOps();
		for (EntityPlayer player : (List<EntityPlayer>)manager.playerEntityList) {
			if (ops.contains(player.username.toLowerCase())) {
				PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
			}
		}
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
				parsedPacket.execute(player);
			} catch (Exception e) {
				logger.warning("Exception during packet handling: " + e.getClass().getSimpleName() + " (" + e.getMessage() + ")");
				e.printStackTrace();
			}
		}
	}
}
