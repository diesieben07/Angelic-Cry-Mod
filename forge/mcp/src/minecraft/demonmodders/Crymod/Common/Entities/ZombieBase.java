package demonmodders.Crymod.Common.Entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.world.World;
import demonmodders.Crymod.Common.Entities.Ai.EntityAIFollowOwner;

public abstract class ZombieBase extends SummonableBase {
	
	public ZombieBase(World world) {
		super(world);
		moveSpeed = 0.23F;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackOnCollide(this, getTarget(), moveSpeed, false));
		tasks.addTask(2, new EntityAIFollowOwner(this, moveSpeed, 2, 10));
		tasks.addTask(3, new EntityAIWander(this, moveSpeed));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, getTarget(), 20, 0, true));
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(this.boundingBox); // && worldObj.isBlockSolidOnSide(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), ForgeDirection.UP); 
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}
	
	public abstract Class<? extends EntityLiving> getTarget();

}