package demonmodders.Crymod.Common.Entities;

import net.minecraft.src.EntityAIHurtByTarget;
import net.minecraft.src.EntityAINearestAttackableTarget;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

public class EntityHeavenZombie extends ZombieBase {
	
	public EntityHeavenZombie(World world) {
		super(world);
		texture = "/crymodResource/tex/mob/heavenZombie.png";
	}

	@Override
	public Class<? extends EntityLiving> getTarget() {
		return EntityHellZombie.class;
	}
}