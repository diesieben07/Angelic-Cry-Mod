package demonmodders.crymod.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import demonmodders.crymod.common.Crymod;

public abstract class CrymodEntityFX extends EntityFX {

	private final RenderEngine engine;
	
	public CrymodEntityFX(RenderEngine engine, World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
		this.engine = engine;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}
	
	@Override
	public void renderParticle(Tessellator tesselator, float par2, float par3, float par4, float par5, float par6, float par7) {
		engine.bindTexture(engine.getTexture(Crymod.PARTICLE_TEXTURE_FILE));
		super.renderParticle(tesselator, par2, par3, par4, par5, par6, par7);
	}
	
	public CrymodEntityFX setMaxAge(int maxAge) {
		particleMaxAge = maxAge;
		return this;
	}
	
	public CrymodEntityFX makeMovementNoise() {
		this.motionX = motionX + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F);
        this.motionY = motionY + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F);
        this.motionZ = motionZ + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F);
        float var14 = (float)(Math.random() + Math.random() + 1.0D) * 0.15F;
        float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / (double)var15 * (double)var14 * 0.4000000059604645D;
        this.motionY = this.motionY / (double)var15 * (double)var14 * 0.4000000059604645D + 0.10000000149011612D;
        this.motionZ = this.motionZ / (double)var15 * (double)var14 * 0.4000000059604645D;
        return this;
	}
}