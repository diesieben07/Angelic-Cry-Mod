package demonmodders.crymod.client.gui;

import net.minecraft.util.StringTranslate;

import org.lwjgl.opengl.GL11;

import demonmodders.crymod.common.gui.ContainerRechargeStation;
import demonmodders.crymod.common.tileentities.TileEntityRechargeStation;

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
	String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/rechargeStation.png";
	}
}
