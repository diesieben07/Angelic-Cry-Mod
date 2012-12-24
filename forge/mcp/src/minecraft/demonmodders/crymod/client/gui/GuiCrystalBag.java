package demonmodders.crymod.client.gui;

import net.minecraft.util.StringTranslate;

import org.lwjgl.opengl.GL11;

import demonmodders.crymod.common.gui.ContainerCrystalBag;
import demonmodders.crymod.common.inventory.InventoryCrystalBag;

public class GuiCrystalBag extends AbstractGuiContainer<ContainerCrystalBag, InventoryCrystalBag> {

	public GuiCrystalBag(ContainerCrystalBag container) {
		super(container);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRenderer.drawString(StringTranslate.getInstance().translateKey("item.crystalBag.name"), 60, 6, 0x404040);
		fontRenderer.drawString(StringTranslate.getInstance().translateKey("container.inventory"), 8, ySize - 94, 0x404040);
	}

	@Override
	String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/crystalBag.png";
	}
}