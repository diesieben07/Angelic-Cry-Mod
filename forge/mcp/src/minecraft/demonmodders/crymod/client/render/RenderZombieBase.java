package demonmodders.crymod.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;

public class RenderZombieBase extends RenderBiped {

	private static boolean renderLabel = true;
	
	public RenderZombieBase() {
		super(new ModelBiped(), 0.5F);
	}

	@Override
	protected void passSpecialRender(EntityLiving living, double x, double y, double z) {
		if (renderLabel) {
			renderLivingLabel(living, living.getEntityName(), x, y, z, 64);
		}
	}
	
	public static void enableLabel() {
		renderLabel = true;
	}
	
	public static void disableLabel() {
		renderLabel = false;
	}
}
