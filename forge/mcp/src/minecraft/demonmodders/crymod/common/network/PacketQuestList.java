package demonmodders.crymod.common.network;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import demonmodders.crymod.common.entities.EntityQuester;
import demonmodders.crymod.common.quest.Quest;

public class PacketQuestList extends CrymodPacket {

	private int quester;
	private List<Quest> quests;
	
	public PacketQuestList() { }
	
	public PacketQuestList(EntityQuester quester) {
		this.quester = quester.entityId;
		quests = quester.getQuests();
	}

	@Override
	void writeData(ByteArrayDataOutput out) {
		out.writeInt(quester);
		
		out.writeByte(quests.size());
		
		for (Quest quest : quests) {
			quest.write(out);
		}
	}

	@Override
	void readData(ByteArrayDataInput in) {
		quester = in.readInt();
		
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
			Entity entity = player.worldObj.getEntityByID(quester);
			if (entity instanceof EntityQuester) {
				((EntityQuester)entity).setQuests(quests);
			}
		}
	}
}