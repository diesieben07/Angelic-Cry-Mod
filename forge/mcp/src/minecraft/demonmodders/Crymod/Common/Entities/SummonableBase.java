package demonmodders.crymod.common.entities;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public abstract class SummonableBase extends EntityCreature implements IEntityAdditionalSpawnData {

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeUTF(name);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		name = data.readUTF();
	}

	String owner = "";
	String name = "";
	
	public SummonableBase(World world) {
		super(world);
	}
	
	public EntityPlayer getOwner() {
		return worldObj.getPlayerEntityByName(owner);
	}
	
	public SummonableBase setOwner(EntityPlayer owner) {
		this.owner = owner.username;
		return this;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setString("owner", owner);
		nbt.setString("name", name);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		owner = nbt.getString("owner");
		name = nbt.getString("name");
	}

	@Override
	public String getEntityName() {
		return name;
	}

	@Override
	public void initCreature() {
		name = RANDOM_NAMES[rand.nextInt(RANDOM_NAMES.length)];
	}
	
	private static final String[]  RANDOM_NAMES = new String[] {
		"Name 1", "Name 2", "Name 3"
	};
}
