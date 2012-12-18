package demonmodders.Crymod.Common.Entities.Ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import demonmodders.Crymod.Common.Entities.ZombieBase;

public class EntityAIFollowOwner extends EntityAIBase {

	private final ZombieBase zombie;
	private final float minDistance;
	private final float maxDistance;
	private final PathNavigate navigator;
	private final float moveSpeed;
	
	private int moveTimeout;
	
	@Override
	public void startExecuting() {
		moveTimeout = 0;
	}

	public EntityAIFollowOwner(ZombieBase zombie, float moveSpeed, float minDistance, float maxDistance) {
		this.zombie = zombie;
		this.maxDistance = maxDistance;
		this.minDistance = minDistance;
		this.moveSpeed = moveSpeed;
		navigator = zombie.getNavigator();
	}
	
	@Override
	public void updateTask() {
		EntityPlayer owner = zombie.getOwner();
		zombie.getLookHelper().setLookPositionWithEntity(owner, 10, zombie.getVerticalFaceSpeed());
		if (--moveTimeout <= 0) {
			moveTimeout = 10;
			zombie.getNavigator().tryMoveToEntityLiving(owner, moveSpeed);
		}
		
	}

	@Override
	public void resetTask() {
		navigator.clearPathEntity();
	}

	@Override
	public boolean shouldExecute() {
		EntityPlayer owner = zombie.getOwner();
		return owner != null && owner.getDistanceSqToEntity(zombie) > maxDistance * maxDistance;
	}

}
