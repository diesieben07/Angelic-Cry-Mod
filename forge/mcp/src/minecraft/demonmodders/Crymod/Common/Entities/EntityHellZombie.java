package demonmodders.Crymod.Common.Entities;

import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntityAIHurtByTarget;
import net.minecraft.src.EntityAILookIdle;
import net.minecraft.src.EntityAINearestAttackableTarget;
import net.minecraft.src.EntityAISwimming;
import net.minecraft.src.EntityAIWander;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

public class EntityHellZombie extends ZombieBase {

	public EntityHellZombie(World world) {
		super(world);
		texture = "/crymodResource/tex/mob/hellZombie.png";
	}

	@Override
	public Class<? extends EntityLiving> getTarget() {
		return EntityHeavenZombie.class;
	}
}