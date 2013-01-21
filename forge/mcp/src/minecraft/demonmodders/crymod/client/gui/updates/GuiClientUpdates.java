package demonmodders.crymod.client.gui.updates;

import org.lwjgl.input.Keyboard;

import demonmodders.crymod.common.Crymod;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StringTranslate;

public class GuiClientUpdates extends GuiUpdates {

	private final GuiScreen parent;
	
	public GuiClientUpdates(GuiScreen parent) {
		this.parent = parent;
		Crymod.updater.registerHandler(this);
	}

	@Override
	protected void keyTyped(char keyChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			done();
		}
	}

	@Override
	protected void done() {
		mc.displayGuiScreen(parent);
	}

	@Override
	protected void retry() {
		Crymod.updater.startIfNotRunning();
	}

	@Override
	protected void update() {
		Crymod.updater.startDownload();
	}
	
	@Override
	public void initGui() {
		headingText = StringTranslate.getInstance().translateKey("crymod.ui.updates");
		super.initGui();
	}
}