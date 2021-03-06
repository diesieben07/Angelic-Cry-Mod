package demonmodders.crymod.common.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.world.World;
import demonmodders.crymod.common.entities.ai.EntityAIFollowOwner;

public abstract class ZombieBase extends EntitySummonable {
	
	@Override
	public int getRank() {
		return 5;
	}

	public ZombieBase(World world) {
		super(world);
		moveSpeed = 0.23F;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIFollowOwner(this, moveSpeed, 2, 10));
		tasks.addTask(3, new EntityAIWander(this, moveSpeed));
		tasks.addTask(4, new EntityAILookIdle(this));
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
}