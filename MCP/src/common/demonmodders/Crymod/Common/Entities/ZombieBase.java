package demonmodders.Crymod.Common.Entities;

import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntityAIHurtByTarget;
import net.minecraft.src.EntityAILookIdle;
import net.minecraft.src.EntityAINearestAttackableTarget;
import net.minecraft.src.EntityAISwimming;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityCreature;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

public abstract class ZombieBase extends EntityCreature {

	public ZombieBase(World world) {
		super(world);
		moveSpeed = 0.23F;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackOnCollide(this, getTarget(), moveSpeed, false));
		tasks.addTask(2, new EntityAIWander(this, moveSpeed));
		tasks.addTask(3, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, getTarget(), 20, 0, true));
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}
	
	public abstract Class<? extends EntityLiving> getTarget();

}