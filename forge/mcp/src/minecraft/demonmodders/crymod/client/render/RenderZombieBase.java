package demonmodders.crymod.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;

public class RenderZombieBase extends RenderLiving {

	public RenderZombieBase() {
		super(new ModelBiped(), 0.5F);
	}

	@Override
	protected void passSpecialRender(EntityLiving living, double x, double y, double z) {
		renderLivingLabel(living, living.getEntityName(), x, y, z, 64);
	}
}
