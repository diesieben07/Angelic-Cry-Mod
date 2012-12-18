package demonmodders.crymod1.common.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityHellZombie extends ZombieBase {

	public EntityHellZombie(World world) {
		super(world);
		texture = "/demonmodders/crymod/resource/tex/mob/hellZombie.png";
	}

	@Override
	public Class<? extends EntityLiving> getTarget() {
		return EntityHeavenZombie.class;
	}
}