package demonmodders.crymod.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.ITinyPacketHandler;
import cpw.mods.fml.common.network.Player;

public class CrymodPacketHandler implements IPacketHandler, ITinyPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput in = ByteStreams.newDataInput(packet.data);
		CrymodPacket.execute(in, in.readByte(), (EntityPlayer)player);
	}

	@Override
	public void handle(NetHandler handler, Packet131MapData mapData) {
		CrymodPacket.execute(ByteStreams.newDataInput(mapData.itemData), mapData.uniqueID, handler.getPlayer());
	}
}