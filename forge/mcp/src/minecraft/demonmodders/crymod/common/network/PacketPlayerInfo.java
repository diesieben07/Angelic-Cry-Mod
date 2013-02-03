package demonmodders.crymod.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod.common.playerinfo.PlayerInformation;

public class PacketPlayerInfo extends CrymodPacket {

	private float karma;
	private int flyTime;
	private byte[] enderBook;
	
	public PacketPlayerInfo() { }
	
	public PacketPlayerInfo(PlayerInformation playerInformation) {
		karma = playerInformation.getKarma();
		flyTime = playerInformation.getFlyTime();
		enderBook = playerInformation.getEnderBookRecipesRaw();
	}
	
	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeFloat(karma);
		out.writeShort(flyTime);
		out.writeByte(enderBook.length);
		out.write(enderBook);
	}

	@Override
	void readData(ByteArrayDataInput in) {
		karma = in.readFloat();
		flyTime = in.readShort();
		enderBook = new byte[in.readUnsignedByte()];
		in.readFully(enderBook);
	}

	@Override
	void execute(EntityPlayer player) {
		if (!(player instanceof EntityPlayerMP)) {
			PlayerInformation info = PlayerInformation.forPlayer(player);
			info.setFlyTime(flyTime);
			info.setKarma(karma);
			info.setEnderBookRecipes(enderBook);
		}
	}
}