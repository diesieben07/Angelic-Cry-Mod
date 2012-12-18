package demonmodders.Crymod.Client.Gui;

import net.minecraft.util.StringTranslate;

import org.lwjgl.opengl.GL11;

import demonmodders.Crymod.Common.Gui.ContainerRechargeStation;
import demonmodders.Crymod.Common.TileEntities.TileEntityRechargeStation;

public class GuiRechargeStation extends AbstractGuiContainer<ContainerRechargeStation, TileEntityRechargeStation> {

	public GuiRechargeStation(ContainerRechargeStation container) {
		super(container);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRenderer.drawString(StringTranslate.getInstance().translateKey("tile.rechargeStation.name"), 28, 6, 0x404040);
		fontRenderer.drawString(StringTranslate.getInstance().translateKey("container.inventory"), 8, ySize - 94, 0x404040);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int texture = mc.renderEngine.getTexture("/crymodResource/tex/rechargeStation.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(texture);
        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, this.xSize, this.ySize);
	}

}