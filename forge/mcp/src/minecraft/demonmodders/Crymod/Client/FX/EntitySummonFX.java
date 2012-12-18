package demonmodders.crymod.client.fx;

import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.world.World;

public class EntitySummonFX extends CrymodEntityFX {

	public EntitySummonFX(RenderEngine engine, World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(engine, world, x, y, z, motionX, motionY, motionZ);
		setParticleTextureIndex(0);
		particleMaxAge = 30;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}

}