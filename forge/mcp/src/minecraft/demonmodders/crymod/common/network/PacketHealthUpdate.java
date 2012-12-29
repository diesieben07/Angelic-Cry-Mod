package demonmodders.crymod.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class PacketHealthUpdate extends CrymodPacket {

	private int entityId;
	private int health;

	public PacketHealthUpdate() { }

	public PacketHealthUpdate(int entityId, int health) {
		this.entityId = entityId;
		this.health = health;
	}

	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeInt(entityId);
		out.writeByte(health);
	}

	@Override
	void readData(ByteArrayDataInput in) {
		entityId = in.readInt();
		health = in.readByte();
	}

	@Override
	void execute(EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			return;
		}
		
		Entity entity = player.worldObj.getEntityByID(entityId);
		if (entity != null && entity instanceof EntityLiving) {
			((EntityLiving)entity).setEntityHealth(health);
		}
	}
}
