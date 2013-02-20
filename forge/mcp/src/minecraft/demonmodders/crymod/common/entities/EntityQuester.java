package demonmodders.crymod.common.entities;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.primitives.UnsignedBytes;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.container.GuiType;
import demonmodders.crymod.common.network.PacketQuestList;
import demonmodders.crymod.common.quest.Quest;
import demonmodders.crymod.common.quest.QuestGenerator;
import demonmodders.crymod.common.quest.QuestKillEntity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class EntityQuester extends EntityLiving implements IEntityAdditionalSpawnData {

	private Type type = Type.OLD_MAN;
	private List<Quest> quests = Lists.newArrayList();
	
	public EntityQuester(World world) {
		super(world);
		generatePersistentID();
	}
	
	public List<Quest> getQuests() {
		return quests;
	}
	
	public void setQuests(Collection<Quest> quests) {
		this.quests = Lists.newArrayList(quests);
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	@Override
	public void initCreature() {
		super.initCreature();
		type = Type.random(rand);
		QuestGenerator.instance().generateQuests(this, quests, 5);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("questerType", UnsignedBytes.checkedCast(type.id()));
		nbt.setTag("quests", Quest.writeQuests(quests));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		type = Type.byId(UnsignedBytes.toInt(nbt.getByte("questerType")));
		
		Quest.readQuests(nbt.getTagList("quests"), quests);
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeByte(type.id());
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		type = Type.byId(data.readUnsignedByte());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTexture() {
		return type == null ? "" : type.getTexture();
	}
	
	public static enum Type {
		OLD_MAN("oldManQuester");
		
		private final String texture;
		
		private Type(String texture) {
			this.texture = "/demonmodders/crymod/resource/tex/mob/" + texture + ".png";
		}
		
		public String getTexture() {
			return texture;
		}
		
		public int id() {
			return ordinal();
		}
		
		public static Type byId(int id) {
			return id < 0 || id >= values().length ? OLD_MAN : values()[id];
		}
		
		public static Type random(Random rng) {
			return values()[rng.nextInt(values().length)];
		}
	}

	@Override
	public boolean interact(EntityPlayer player) {
		if (!worldObj.isRemote) {
			new PacketQuestList(this).sendToPlayer(player);
			player.openGui(Crymod.instance, GuiType.QUESTS.getGuiId(), player.worldObj, entityId, 0, 0);
		}
		return true;
	}
}