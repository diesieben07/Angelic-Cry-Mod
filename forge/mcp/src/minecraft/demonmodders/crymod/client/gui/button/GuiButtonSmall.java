package demonmodders.crymod.client.gui.button;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonSmall extends GuiButton {

	public GuiButtonSmall(int id, int x, int y, int width, String text) {
		super(id, x, y, width, 13, text);
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (drawButton) {
			FontRenderer var4 = mc.fontRenderer;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/demonmodders/crymod/resource/tex/gui.png"));
			GL11.glColor3f(1, 1, 1);
			field_82253_i = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			int hoverState = getHoverState(field_82253_i);
			drawTexturedModalRect(xPosition, yPosition, 0, 50 + hoverState * 13, width / 2, height);
			drawTexturedModalRect(xPosition + width / 2, yPosition, 77 - this.width / 2, 50 + hoverState * 13, width / 2, height);

			int textColor = 0xe0e0e0;

			if (!enabled) {
				textColor = 0xa0a0a0;
			} else if (field_82253_i) {
				textColor = 0xffffa0;
			}

			drawCenteredString(var4, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, textColor);
		}
	}
}