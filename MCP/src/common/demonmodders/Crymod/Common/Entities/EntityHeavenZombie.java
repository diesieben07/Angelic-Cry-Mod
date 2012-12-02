package demonmodders.Crymod.Common.Entities;

import net.minecraft.src.EntityCreature;
import net.minecraft.src.World;

public class EntityHeavenZombie extends EntityCreature {
	
	public EntityHeavenZombie(World world) {
		this(world, 20);
	}
	
	public EntityHeavenZombie(World world, int maxHealth) {
		super(world);
		texture = "/crymodResource/tex/mob/heavenZombie.png";
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

}
