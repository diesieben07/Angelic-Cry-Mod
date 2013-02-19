package demonmodders.crymod.client.gui;

import demonmodders.crymod.common.container.ContainerMagiciser;
import demonmodders.crymod.common.tileentities.TileEntityMagiciser;

public class GuiMagiciser extends AbstractGuiContainer<ContainerMagiciser, TileEntityMagiciser> {

	public GuiMagiciser(ContainerMagiciser container) {
		super(container);
		xSize = 176;
		ySize = 166;
	}

	@Override
	protected String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/magiciser.png";
	}

}
