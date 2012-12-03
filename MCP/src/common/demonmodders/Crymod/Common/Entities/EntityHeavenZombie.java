package demonmodders.Crymod.Common.Entities;

import demonmodders.Crymod.Common.Network.PacketClientEffect;
import net.minecraft.src.EntityAIHurtByTarget;
import net.minecraft.src.EntityAINearestAttackableTarget;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

public class EntityHeavenZombie extends ZombieBase {
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		//new PacketClientEffect(PacketClientEffect.Type.SUMMON_GOOD, posX, posY + 3, posZ, motionX, motionY, motionZ).sendToAllNear(this, 64);
	}

	public EntityHeavenZombie(World world) {
		super(world);
		texture = "/crymodResource/tex/mob/heavenZombie.png";
	}

	@Override
	public Class<? extends EntityLiving> getTarget() {
		return EntityHellZombie.class;
	}
}