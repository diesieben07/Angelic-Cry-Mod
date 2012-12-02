package demonmodders.Crymod.Common.Entities;

import net.minecraft.src.EntityAIAvoidEntity;
import net.minecraft.src.EntityAILookIdle;
import net.minecraft.src.EntityAISwimming;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class EntityHeavenZombie extends SummonableEntity {
	
	public EntityHeavenZombie(World world) {
		this(world, 20);
	}
	
	public EntityHeavenZombie(World world, int maxHealth) {
		super(world);
		texture = "/crymodResource/tex/mob/heavenZombie.png";
		moveSpeed = 0.23F;
		int taskCount = 0;
		tasks.addTask(taskCount++, new EntityAISwimming(this));
		tasks.addTask(taskCount++, new EntityAIWander(this, moveSpeed));
		tasks.addTask(taskCount++, new EntityAILookIdle(this));
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	@Override
	public boolean isAngel() {
		return true;
	}

}