package demonmodders.crymod.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod.common.entities.SummonableBase;

public class PacketNameUpdate extends CrymodPacket {

	private int entityId;
	private String newName;
	
	public PacketNameUpdate(int entityId, String newName) {
		this.entityId = entityId;
		this.newName = newName;
	}

	public PacketNameUpdate() { }

	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeInt(entityId);
		out.writeUTF(newName);
	}

	@Override
	void readData(ByteArrayDataInput in) {
		entityId = in.readInt();
		newName = in.readUTF();
	}

	@Override
	void execute(EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			return;
		}
		Entity target = player.worldObj.getEntityByID(entityId);
		if (target != null && target instanceof SummonableBase) {
			((SummonableBase)target).setName(newName);
		}
	}
}