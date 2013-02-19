package demonmodders.crymod.common.quest;

import java.util.Random;
import java.util.UUID;

import com.google.common.base.Strings;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringTranslate;
import demonmodders.crymod.common.entities.EntityQuester;
import demonmodders.crymod.common.playerinfo.PlayerInformation;

public class QuestKillEntity extends Quest {

	private Class<? extends EntityLiving> toKill;
	private int neededKillCount;
	
	private int killed = 0;
	
	public QuestKillEntity(UUID questerUid) {
		super(questerUid);
	}
	
	public QuestKillEntity(UUID questerUid, Class<? extends EntityLiving> toKill, int count) {
		this(questerUid);
		this.toKill = toKill;
		neededKillCount = count;
	}

	@Override
	protected void readDataFromNbt(NBTTagCompound nbt) {
		toKill = (Class<? extends EntityLiving>) EntityList.stringToClassMapping.get(nbt.getString("targetType"));
		neededKillCount = nbt.getInteger("killCount");
		killed = nbt.getInteger("killed");
	}

	@Override
	protected void writeDataToNbt(NBTTagCompound nbt) {
		nbt.setString("targetType", (String)EntityList.classToStringMapping.get(toKill));
		nbt.setInteger("killCount", neededKillCount);
		nbt.setInteger("killed", killed);
	}
	
	@Override
	protected void writeData(ByteArrayDataOutput out) {
		out.writeUTF((String)EntityList.classToStringMapping.get(toKill));
		out.writeInt(neededKillCount);
	}

	@Override
	protected void readData(ByteArrayDataInput in) {
		toKill = (Class<? extends EntityLiving>) EntityList.stringToClassMapping.get(in.readUTF());
		neededKillCount = in.readInt();
	}

	@Override
	public void onComplete() {
		PlayerInformation.forPlayer(executingPlayer).modifyKarma(2);
	}

	@Override
	public boolean hasCompleted() {
		return killed == neededKillCount;
	}

	@Override
	public void onKillEntity(EntityLiving killed) {
		if (this.killed < neededKillCount && toKill.isAssignableFrom(killed.getClass())) {
			this.killed++;
			recheck();
		}
	}

	@Override
	public void makeRandom(Random random) {
		switch (random.nextInt(4)) {
		case 0:
			toKill = EntityZombie.class;
			break;
		case 1:
			toKill = EntitySkeleton.class;
			break;
		case 2:
			toKill = EntityCreeper.class;
			break;
		case 3:
			toKill = EntitySpider.class;
			break;
		}
		neededKillCount = 3 + random.nextInt(5);
	}

	@Override
	public String getTitle() {
		String entityName = (String) EntityList.classToStringMapping.get(toKill);
		return new StringBuilder()
		.append("Kill ")
		.append(neededKillCount)
		.append(" of ")
		.append(StringTranslate.getInstance().translateKey("entity." + Strings.nullToEmpty(entityName) + ".name"))
		.toString();
	}
}