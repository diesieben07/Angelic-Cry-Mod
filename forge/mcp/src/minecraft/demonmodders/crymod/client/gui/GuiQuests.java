package demonmodders.crymod.client.gui;

import net.minecraft.inventory.IInventory;
import demonmodders.crymod.client.gui.button.GuiButtonTextOnly;
import demonmodders.crymod.common.container.ContainerQuests;
import demonmodders.crymod.common.quest.Quest;

public class GuiQuests extends AbstractGuiContainer<ContainerQuests, IInventory> {

	public GuiQuests(ContainerQuests container) {
		super(container);
	}

	@Override
	public void initGui() {
		int btnCount = 0;
		for (Quest quest : container.getQuester().getQuests()) {
			controlList.add(new GuiButtonTextOnly(-btnCount, 10, 10 + (fontRenderer.FONT_HEIGHT + 3) * btnCount, quest.getTitle(), fontRenderer));
			btnCount++;
		}
	}

	@Override
	protected String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/questingInterface.png";
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}