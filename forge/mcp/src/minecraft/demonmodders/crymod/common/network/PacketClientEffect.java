package demonmodders.crymod.common.network;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod.common.Crymod;

public class PacketClientEffect extends CrymodPacket {

	public Type type;
	public double x;
	public double y;
	public double z;
	
	public PacketClientEffect() {}
	
	public PacketClientEffect(Type type, double x, double y, double z) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeByte(type.ordinal());
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
	}

	@Override
	void readData(ByteArrayDataInput in) {
		int typeId = in.readByte();
		x = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		
		if (typeId >= 0 && typeId < Type.values().length) {
			type = Type.values()[typeId];
		} else {
			type = null;
		}
	}

	@Override
	void execute(EntityPlayer player) {
		if (type != null) {
			Crymod.proxy.handleClientEffect(this);
		}
	}

	public static enum Type {
		SUMMON_GOOD, SUMMON_BAD, SWORD_OF_DARKNESS, ENDERBOOK
	}
	
}
