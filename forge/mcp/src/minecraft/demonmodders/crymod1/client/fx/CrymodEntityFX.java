package demonmodders.crymod1.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import demonmodders.crymod1.common.Crymod;

public abstract class CrymodEntityFX extends EntityFX {

	private final RenderEngine engine;
	
	public CrymodEntityFX(RenderEngine engine, World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
		this.engine = engine;
	}
	
	@Override
	public void renderParticle(Tessellator tesselator, float par2, float par3, float par4, float par5, float par6, float par7) {
		engine.bindTexture(engine.getTexture(Crymod.PARTICLE_TEXTURE_FILE));
		super.renderParticle(tesselator, par2, par3, par4, par5, par6, par7);
	}
}