package demonmodders.crymod.client.gui.entityinfo;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiEntityRename extends GuiScreen {

	private final GuiScreen parentScreen;

	public GuiEntityRename(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	protected void keyTyped(char character, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(parentScreen);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		
	}

	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		super.initGui();
	}
	
	
	
}