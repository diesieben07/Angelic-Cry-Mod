package demonmodders.crymod1.common.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityHeavenZombie extends ZombieBase {
	
	public EntityHeavenZombie(World world) {
		super(world);
		texture = "/demonmodders/crymod/resource/tex/mob/heavenZombie.png";
	}

	@Override
	public Class<? extends EntityLiving> getTarget() {
		return EntityHellZombie.class;
	}
}