package demonmodders.Crymod.Common.Entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

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