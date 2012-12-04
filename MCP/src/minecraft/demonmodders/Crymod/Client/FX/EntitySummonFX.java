package demonmodders.Crymod.Client.FX;

import net.minecraft.src.RenderEngine;
import net.minecraft.src.World;

public class EntitySummonFX extends CrymodEntityFX {

	public EntitySummonFX(RenderEngine engine, World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(engine, world, x, y, z, motionX, motionY, motionZ);
		setParticleTextureIndex(0);
		particleMaxAge = 200;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}

}