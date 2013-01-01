package demonmodders.crymod.common.network;

import static demonmodders.crymod.common.Crymod.logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import demonmodders.crymod.common.Crymod;

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
		addMapping(4, PacketEnderBookRecipe.class);
		addMapping(5, PacketHealthUpdate.class);
		addMapping(6, PacketRenameEntity.class);
		addMapping(7, PacketNameUpdate.class);
	}
	
	public final Packet generatePacket() {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		Integer packetId = classToIdMap.get(getClass());
		
		if (packetId == null) {
			logger.warning("Packet " + getClass() + " is missing a Mapping!");
			return null;
		} else {
			output.writeByte(packetId);
			writeData(output);
			byte[] bytes = output.toByteArray();
			if (bytes.length <= Short.MAX_VALUE + 1) {
				return PacketDispatcher.getTinyPacket(Crymod.instance, bytes[0], Arrays.copyOfRange(bytes, 1, bytes.length));
			} else {
				return new Packet250CustomPayload(CHANNEL, bytes);
			}
		}
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
				e.printStackTrace();
			}
		}
	}
}
