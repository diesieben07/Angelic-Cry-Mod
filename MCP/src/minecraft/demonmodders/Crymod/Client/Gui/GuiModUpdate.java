package demonmodders.Crymod.Client.Gui;

import org.lwjgl.input.Keyboard;

import demonmodders.Crymod.Common.UpdateChecker;
import demonmodders.Crymod.Common.UpdateChecker.State;
import net.minecraft.src.GuiScreen;

public class GuiModUpdate extends GuiScreen {

	private final GuiScreen parent;
	
	public GuiModUpdate(GuiScreen parent) {
		this.parent = parent;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		drawString(fontRenderer, UpdateChecker.getState().toString(), 10, 10, 0xffffff);
	}

	@Override
	protected void keyTyped(char keyChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(parent);
		} else {
			super.keyTyped(keyChar, keyCode);
		}
	}
}