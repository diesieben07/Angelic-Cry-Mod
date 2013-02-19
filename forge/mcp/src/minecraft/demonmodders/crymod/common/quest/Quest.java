package demonmodders.crymod.common.quest;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.primitives.UnsignedBytes;

import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.CrymodUtils;
import demonmodders.crymod.common.entities.EntityQuester;
import demonmodders.crymod.common.playerinfo.PlayerInformation;

public abstract class Quest {
	
	/**
	 * the entityUUID of the quester that gave this quest
	 */
	protected final UUID questerUid;
	
	/**
	 * the player that accepted this quest or null if none
	 */
	protected EntityPlayer executingPlayer = null;
	
	private boolean completionProcessed = false;
	
	public Quest(UUID questerUid) {
		this.questerUid = questerUid;
	}
	
	/**
	 * assign this quest to the given player
	 * @param player player that accepted this quest
	 */
	public final void setPlayer(EntityPlayer player) {
		executingPlayer = player;
	}
	
	/**
	 * return false here if the player can't accept this quest
	 * @param player the player to check
	 * @return false to disallow acception
	 */
	public boolean canAccept(EntityPlayer player) {
		return true;
	}
	
	/**
	 * called when a player accepts this quest ({@link #setPlayer} is invoked from here, too)
	 * @param player the player that accepted
	 * @param quester the quester that offered the quest
	 */
	public void onAccept(EntityPlayer player, EntityQuester quester) {
		setPlayer(player);
		PlayerInformation.forPlayer(player).addQuest(this);
		player.sendChatToPlayer("You accepted quest: " + CrymodUtils.color(getTitle(), "3"));
	}
	
	/**
	 * called when a player has accepted this quest and then kills an EntityLiving 
	 * @param killed the entity killed
	 */
	public void onKillEntity(EntityLiving killed) { }
	
	/**
	 * rechecks this quests state
	 */
	public final void recheck() {
		if (hasCompleted() && !completionProcessed) {
			completionProcessed = true;
			onComplete();
			executingPlayer.sendChatToPlayer("You have completed the Quest: " + CrymodUtils.color(getTitle(), "3"));
		}
	}
	
	/**
	 * initialize this quest with random values
	 * @param random
	 */
	public abstract void makeRandom(Random random);
	
	/**
	 * called when the player completes this quest
	 */
	public abstract void onComplete();
	
	/**
	 * checks if the quest has been completed
	 * @return true if completed
	 */
	public abstract boolean hasCompleted();
	
	public abstract String getTitle();
	
	protected final byte getTypeId() {
		return UnsignedBytes.checkedCast(questTypes.inverse().get(getClass()));
	}
	
	/**
	 * Saves this Quest to nbt
	 * @param nbt tag to save to
	 * @return the nbt tag for convenience
	 */
	public final NBTTagCompound writeToNbt(NBTTagCompound nbt) {
		nbt.setByte("typeId", getTypeId());
		nbt.setLong("questerUidMSB", questerUid.getMostSignificantBits());
		nbt.setLong("questerUidLSB", questerUid.getLeastSignificantBits());
		writeDataToNbt(nbt);
		return nbt;
	}
	
	/**
	 * reconstructs a quest from disk
	 * @param nbt NBTTagCompound to reconstruct from
	 * @return the constructed quest or null if it failed
	 */
	public static Quest readFromNbt(NBTTagCompound nbt) {
		int id = UnsignedBytes.toInt(nbt.getByte("typeId"));
		UUID questerUid = new UUID(nbt.getLong("questerUidMSB"), nbt.getLong("questerUidLSB"));
		Quest quest = constructQuest(id, questerUid);
		quest.readDataFromNbt(nbt);
		return quest;
	}
	
	private static Quest constructQuest(int id, UUID questerUid) {
		Class<? extends Quest> clazz = questTypes.get(Integer.valueOf(id));
		try {
			Quest quest = clazz.getConstructor(UUID.class).newInstance(questerUid);
			return quest;
		} catch (ReflectiveOperationException e) {
			Crymod.logger.warning("Warning: failed to reconstruct Quest!");
			e.printStackTrace();
			return null;
		}
	}
	
	public final void write(ByteArrayDataOutput out) {
		out.writeByte(getTypeId());
		writeData(out);
	}
	
	public static Quest read(ByteArrayDataInput in) {
		int id = in.readUnsignedByte();
		Quest quest = constructQuest(id, null);
		quest.readData(in);
		return quest;
	}
	
	protected abstract void writeData(ByteArrayDataOutput out);
	
	protected abstract void readData(ByteArrayDataInput in);
	
	/**
	 * called to reconstruct this quest from disk
	 * @param nbt the NBTTagCompound to reconstruct from
	 */
	protected void readDataFromNbt(NBTTagCompound nbt) { }
	
	/**
	 * called to save this quest to disk
	 * @param nbt the NBTTagCompound to save this quest to
	 */
	protected void writeDataToNbt(NBTTagCompound nbt) { }
	
	/**
	 * writes a list of quests to NBT
	 * @param quests the quests to write
	 * @return a NBTTagList containing the quests 
	 */
	public static NBTTagList writeQuests(List<Quest> quests) {
		NBTTagList questList = new NBTTagList();
		for (Quest quest : quests) {
			questList.appendTag(quest.writeToNbt(new NBTTagCompound()));
		}
		return questList;
	}
	
	/**
	 * reads a list of quests from NBT and stores them into the passed list
	 * @param questList the NBTTagList to read from
	 * @param quests the List to store the quests to
	 */
	public static void readQuests(NBTTagList questList, List<Quest> quests) {
		readQuests(questList, quests, null);
	}

	/**
	 * reads a list of quests from NBT and stores them into the passed list
	 * each quest gets informed that it is assigned to the passed player if non null
	 * @param questList the NBTTagList to read from
	 * @param quests the List to store the quests to
	 * @param player the player the quests should be assigned
	 */
	public static void readQuests(NBTTagList questList, List<Quest> quests, EntityPlayer player) {
		quests.clear();
		for (int i = 0; i < questList.tagCount(); i++) {
			Quest quest = Quest.readFromNbt((NBTTagCompound)questList.tagAt(i));
			if (quest == null) {
				continue;
			}
			quests.add(quest);
			quest.setPlayer(player); // that method can deal with null
		}
	}
	
	private static final BiMap<Integer, Class<? extends Quest>> questTypes = HashBiMap.create();
	
	static {
		questTypes.put(0, QuestKillEntity.class);
	}
}