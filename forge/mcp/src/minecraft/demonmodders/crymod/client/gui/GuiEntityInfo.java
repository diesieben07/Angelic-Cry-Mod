package demonmodders.crymod.client.gui;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import demonmodders.crymod.client.render.RenderZombieBase;
import demonmodders.crymod.common.entities.SummonableBase;
import demonmodders.crymod.common.gui.ContainerEntityInfo;

public class GuiEntityInfo extends AbstractGuiContainer<ContainerEntityInfo, SummonableBase> {

	private int xSizeLo;
	private int ySizeLo;
	
	public GuiEntityInfo(ContainerEntityInfo container) {
		super(container);
		xSize = 248;
		ySize = 195;
	}

	@Override
	String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/creatureInterface.png";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		super.drawGuiContainerBackgroundLayer(var1, var2, var3);
		
		SummonableBase entity = container.getInventoryInstance();
		
		int par1 = guiLeft + 195;
		int par2 = guiTop + 55;
		int scale = 18;
		float par4 = (float)(guiLeft + 51) - xSizeLo;
		float par5 = (float)(guiTop + 75 - 50) - ySizeLo;
		
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par1, (float)par2, 50.0F);
        GL11.glScalef((float)(-scale), (float)scale, (float)scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float var6 = entity.renderYawOffset;
        float var7 = entity.rotationYaw;
        float var8 = entity.rotationPitch;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float)Math.atan((double)(par5 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = (float)Math.atan((double)(par4 / 40.0F)) * 20.0F;
        entity.rotationYaw = (float)Math.atan((double)(par4 / 40.0F)) * 40.0F;
        entity.rotationPitch = -((float)Math.atan((double)(par5 / 40.0F))) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderZombieBase.disableLabel();
        RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        RenderZombieBase.disableLabel();
        entity.renderYawOffset = var6;
        entity.rotationYaw = var7;
        entity.rotationPitch = var8;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		xSizeLo = par1;
		ySizeLo = par2;
	}

}
