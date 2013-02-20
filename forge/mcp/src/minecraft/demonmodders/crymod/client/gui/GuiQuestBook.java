package demonmodders.crymod.client.gui;

import demonmodders.crymod.common.playerinfo.PlayerInformation;
import demonmodders.crymod.common.quest.Quest;
import net.minecraft.client.gui.GuiScreen;

public class GuiQuestBook extends GuiScreen {

	private PlayerInformation playerInfo;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float tickDelta) {
		drawDefaultBackground();
		
		int y = 10;
		for (Quest quest : playerInfo.getActiveQuests()) {
			fontRenderer.drawString(quest.getTitle(), 20, y, 0xffffff);
			y += fontRenderer.FONT_HEIGHT + 5;
		}
		
		super.drawScreen(mouseX, mouseY, tickDelta);
	}

	@Override
	public void initGui() {
		playerInfo = PlayerInformation.forPlayer(mc.thePlayer);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}