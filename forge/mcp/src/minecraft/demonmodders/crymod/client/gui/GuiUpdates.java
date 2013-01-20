package demonmodders.crymod.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringTranslate;

import org.lwjgl.input.Keyboard;

import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.UpdateChecker;
import demonmodders.crymod.common.UpdateChecker.UpdateStatus;
import demonmodders.crymod.common.UpdateChecker.UpdateStatusHandler;

public class GuiUpdates extends GuiScreen implements UpdateStatusHandler {

	private static final int BUTTON_DONE = 0;
	private static final int BUTTON_RETRY = 1;
	private static final int BUTTON_UPDATE = 2;
	
	private final GuiScreen parent;
	private String headingText;
	private int headingWidth;
	private String versionInfoPattern;
	private String currentDisplayText = "";
	private UpdateStatus currentStatus = null;
	private List<String> currentUpdateInformation;
	
	private GuiButton buttonUpdate = null;

	public GuiUpdates(GuiScreen parent) {
		this.parent = parent;
		Crymod.updater.registerHandler(this);
	}

	@Override
	protected void keyTyped(char keyChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(parent);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case BUTTON_DONE:
			mc.displayGuiScreen(parent);
			break;
		case BUTTON_RETRY:
			Crymod.updater.startIfNotRunning();
			break;
		case BUTTON_UPDATE:
			break;
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		super.drawScreen(par1, par2, par3);
		fontRenderer.drawString(headingText, width / 2 - headingWidth / 2, 10, 0xffffff);
		fontRenderer.drawString(currentDisplayText, 10, 40, 0xffffff);
		if (currentUpdateInformation != null) {
			String versioninfo = String.format(versionInfoPattern, UpdateChecker.VERSION, currentUpdateInformation.get(1), UpdateChecker.MINECRAFT_VERSION);
			fontRenderer.drawString(versioninfo, 10, 90, 0xffffff);
			fontRenderer.drawString(currentUpdateInformation.get(2), 10, 80, 0xffffff);
		}
	}

	@Override
	public void handleStatus(UpdateStatus newStatus, List<String> updateInfo) {
		currentDisplayText = newStatus.translate();
		currentStatus = newStatus;
		currentUpdateInformation = updateInfo;
		updateButtons();
	}

	@Override
	public void initGui() {
		headingText = StringTranslate.getInstance().translateKey("crymod.ui.updates");
		headingWidth = fontRenderer.getStringWidth(headingText);
		versionInfoPattern = StringTranslate.getInstance().translateKey("crymod.ui.updates.versioninfo");
		controlList.add(new GuiButton(BUTTON_DONE, width / 2 - 75, height - 38, 150, 20, StringTranslate.getInstance().translateKey("gui.done")));
		controlList.add(new GuiButton(BUTTON_RETRY, 10, 45 + fontRenderer.FONT_HEIGHT, 150, 20, StringTranslate.getInstance().translateKey("crymod.ui.updates.retry")));
		buttonUpdate = new GuiButton(BUTTON_UPDATE, 10, 75 + 4 * fontRenderer.FONT_HEIGHT, 150, 20, StringTranslate.getInstance().translateKey("crymod.ui.updates.update"));
		updateButtons();
		controlList.add(buttonUpdate);
	}
	
	private void updateButtons() {
		if (buttonUpdate != null) {
			buttonUpdate.enabled = currentStatus != null && currentStatus == UpdateStatus.UPDATES_AVAILABLE;
		}
	}
}