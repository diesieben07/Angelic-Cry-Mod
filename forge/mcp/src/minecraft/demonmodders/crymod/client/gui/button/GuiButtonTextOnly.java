package demonmodders.crymod.client.gui.button;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonTextOnly extends GuiButton {

	public GuiButtonTextOnly(int id, int x, int y, int width, int height, String text) {
		super(id, x, y, width, height, text);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (drawButton) {
			field_82253_i = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			int color = 14737632;

			if (!enabled) {
				color = -6250336;
			} else if (this.field_82253_i) {
				color = 16777120;
			}
			
			drawString(mc.fontRenderer, displayString, xPosition, yPosition, color);
		}
	}

}
