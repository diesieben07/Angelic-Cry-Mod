package demonmodders.crymod.client.fx;

import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.world.World;

public class EntityFXTextureChange extends CrymodEntityFX {

	private int beginTextureIndex;
	private int endTextureIndex;
	private int speed;
	private int tickCount = 0;
	private int currentTextureIndex;
	
	public EntityFXTextureChange(RenderEngine engine, World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(engine, world, x, y, z, motionX, motionY, motionZ);
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}
	
	public final EntityFXTextureChange setBeginIndex(int index) {
		beginTextureIndex = currentTextureIndex = index;
		return this;
	}
	
	public final EntityFXTextureChange setEndIndex(int index) {
		endTextureIndex = index;
		return this;
	}
	
	public final EntityFXTextureChange setSpeed(int speed) {
		this.speed = speed;
		return this;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		tickCount++;
		if (tickCount == speed) {
			tickCount = 0;
			currentTextureIndex++;
			if (currentTextureIndex == endTextureIndex + 1) {
				currentTextureIndex = beginTextureIndex;
			}
			setParticleTextureIndex(currentTextureIndex);
		}
	}
}