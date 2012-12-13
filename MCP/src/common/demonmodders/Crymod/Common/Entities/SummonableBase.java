package demonmodders.Crymod.Common.Entities;

import net.minecraft.src.EntityCreature;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public abstract class SummonableBase extends EntityCreature {

	String owner = "";
	
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
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		owner = nbt.getString("owner");
	}
}
