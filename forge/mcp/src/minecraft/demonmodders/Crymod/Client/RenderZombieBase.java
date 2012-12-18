package demonmodders.Crymod.Client;

import demonmodders.Crymod.Common.Entities.ZombieBase;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.RenderLiving;

public class RenderZombieBase extends RenderLiving {

	public RenderZombieBase() {
		super(new ModelBiped(), 0.5F);
	}

	@Override
	protected void passSpecialRender(EntityLiving living, double x, double y, double z) {
		renderLivingLabel(living, living.getEntityName(), x, y, z, 64);
	}
}
