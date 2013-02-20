package demonmodders.crymod.common.network;

import java.util.Collection;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod.common.entities.EntityQuester;
import demonmodders.crymod.common.playerinfo.PlayerInformation;
import demonmodders.crymod.common.quest.Quest;

public class PacketQuestList extends CrymodPacket {

	private int entityId;
	private Collection<Quest> quests;
	
	public PacketQuestList() { }
	
	public PacketQuestList(EntityQuester quester) {
		this.entityId = quester.entityId;
		quests = quester.getQuests();
	}
	
	public PacketQuestList(EntityPlayer player) {
		entityId = player.entityId;
		quests = PlayerInformation.forPlayer(player).getActiveQuests();
	}

	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeInt(entityId);
		
		out.writeByte(quests.size());
		
		for (Quest quest : quests) {
			quest.write(out);
		}
	}

	@Override
	void readData(ByteArrayDataInput in) {
		entityId = in.readInt();
		
		int count = in.readUnsignedByte();
		
		ImmutableList.Builder<Quest> builder = ImmutableList.builder();
		for (int i = 0; i < count; i++) {
			builder.add(Quest.read(in));
		}
		quests = builder.build();
	}

	@Override
	void execute(EntityPlayer player) {
		if (!(player instanceof EntityPlayerMP)) {
			Entity entity = player.worldObj.getEntityByID(entityId);
			if (entity instanceof EntityQuester) {
				((EntityQuester)entity).setQuests(quests);
			} else if (entity instanceof EntityPlayer) {
				PlayerInformation.forPlayer(player).setQuests(quests);
			}
		}
	}
}