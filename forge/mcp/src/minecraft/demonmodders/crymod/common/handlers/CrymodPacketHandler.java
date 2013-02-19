package demonmodders.crymod.common.handlers;

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
import demonmodders.crymod.common.network.CrymodPacket;

public class CrymodPacketHandler implements ITinyPacketHandler {

	@Override
	public void handle(NetHandler handler, Packet131MapData mapData) {
		CrymodPacket.execute(ByteStreams.newDataInput(mapData.itemData), mapData.uniqueID, handler.getPlayer());
	}
}