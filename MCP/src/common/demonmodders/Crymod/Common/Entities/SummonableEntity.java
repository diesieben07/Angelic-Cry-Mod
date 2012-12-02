package demonmodders.Crymod.Common.Entities;

import net.minecraft.src.EntityCreature;
import net.minecraft.src.World;

public abstract class SummonableEntity extends EntityCreature implements Summonable {

	public SummonableEntity(World world) {
		super(world);
	}


}
