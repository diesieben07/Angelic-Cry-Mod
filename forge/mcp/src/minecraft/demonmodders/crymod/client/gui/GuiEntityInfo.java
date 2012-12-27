package demonmodders.crymod.client.gui;

import demonmodders.crymod.common.entities.SummonableBase;
import demonmodders.crymod.common.gui.ContainerEntityInfo;

public class GuiEntityInfo extends AbstractGuiContainer<ContainerEntityInfo, SummonableBase> {

	public GuiEntityInfo(ContainerEntityInfo container) {
		super(container);
		xSize = 248;
		ySize = 195;
	}

	@Override
	String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/creatureInterface.png";
	}

}
