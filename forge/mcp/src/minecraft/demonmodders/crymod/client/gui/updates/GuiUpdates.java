package demonmodders.crymod.client.gui.updates;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringTranslate;
import demonmodders.crymod.common.UpdateChecker;
import demonmodders.crymod.common.UpdateChecker.UpdateStatus;
import demonmodders.crymod.common.UpdateChecker.UpdateStatusHandler;

public abstract class GuiUpdates extends GuiScreen implements UpdateStatusHandler {

	private static final int BUTTON_DONE = 0;
	private static final int BUTTON_RETRY = 1;
	private static final int BUTTON_UPDATE = 2;
	
	protected String headingText;
	private int headingWidth;
	private String versionInfoPattern;
	private String currentDisplayText = "";
	private UpdateStatus currentStatus = null;
	private List<String> currentUpdateInformation;
	
	private GuiButton buttonUpdate = null;
	private GuiButton buttonRecheck = null;
	
	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case BUTTON_DONE:
			done();
			break;
		case BUTTON_RETRY:
			retry();
			break;
		case BUTTON_UPDATE:
			update();
			break;
		}
	}
	
	protected abstract void done();
	
	protected abstract void retry();
	
	protected abstract void update();

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
		currentDisplayText = newStatus == null ? "" : newStatus.translate();
		currentStatus = newStatus;
		currentUpdateInformation = updateInfo;
		updateButtons();
	}


	@Override
	public void initGui() {
		headingWidth = fontRenderer.getStringWidth(headingText);
		versionInfoPattern = StringTranslate.getInstance().translateKey("crymod.ui.updates.versioninfo");
		controlList.add(new GuiButton(BUTTON_DONE, width / 2 - 75, height - 38, 150, 20, StringTranslate.getInstance().translateKey("gui.done")));
		buttonRecheck = new GuiButton(BUTTON_RETRY, 10, 45 + fontRenderer.FONT_HEIGHT, 150, 20, StringTranslate.getInstance().translateKey("crymod.ui.updates.retry"));
		controlList.add(buttonRecheck);
		buttonUpdate = new GuiButton(BUTTON_UPDATE, 10, 75 + 4 * fontRenderer.FONT_HEIGHT, 150, 20, StringTranslate.getInstance().translateKey("crymod.ui.updates.update"));
		controlList.add(buttonUpdate);
		
		updateButtons();
	}
	
	private void updateButtons() {
		if (buttonUpdate != null) {
			buttonUpdate.enabled = currentStatus != null && currentStatus.canInstall();
		}
		if (buttonRecheck != null) {
			buttonRecheck.enabled = currentStatus != null && currentStatus.canRetry();
		}
	}
}