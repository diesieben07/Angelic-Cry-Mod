package demonmodders.crymod.client.gui;

import org.lwjgl.opengl.GL11;

import demonmodders.crymod.common.gui.ContainerEnderBook;
import demonmodders.crymod.common.inventory.InventoryEnderBook;

public class GuiEnderBook extends AbstractGuiContainer<ContainerEnderBook, InventoryEnderBook> {

	public GuiEnderBook(ContainerEnderBook container) {
		super(container);
		xSize = 146;
		ySize = 180;
	}

	@Override
	String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/enderBook.png";
	}
}