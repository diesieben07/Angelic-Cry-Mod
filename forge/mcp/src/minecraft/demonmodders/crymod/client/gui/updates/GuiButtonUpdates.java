package demonmodders.crymod.client.gui.updates;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiButtonUpdates extends GuiButton {

	public GuiButtonUpdates(int x, int y) {
		super(-1, x, y, 20, 20, "");
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.drawButton) {
			mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/demonmodders/crymod/resource/tex/gui.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			boolean mouseOver = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			drawTexturedModalRect(xPosition, yPosition, 0, mouseOver ? 30 : 10, width, height);
			if (mouseOver) {
				drawHoverTooltip(mc, mouseX, mouseY);
			}
		}
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (super.mousePressed(mc, mouseX, mouseY)) {
			mc.sndManager.playSoundFX("random.click", 1, 1);
			mc.displayGuiScreen(new GuiClientUpdates(mc.currentScreen));
		}
		return false;
	}
	
	private void drawHoverTooltip(Minecraft mc, int x, int y) {
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		int stringWidth = mc.fontRenderer.getStringWidth(displayString);
		int actualX = x + 12;
		int actualY = y - 12;
		int tooltipHeight = 8;

		this.zLevel = 300;
		int backgroundColor = 0xf0100010;
		this.drawGradientRect(actualX - 3, actualY - 4, actualX + stringWidth + 3, actualY - 3, backgroundColor, backgroundColor);
		this.drawGradientRect(actualX - 3, actualY + tooltipHeight + 3, actualX + stringWidth + 3, actualY + tooltipHeight + 4, backgroundColor, backgroundColor);
		this.drawGradientRect(actualX - 3, actualY - 3, actualX + stringWidth + 3, actualY + tooltipHeight + 3, backgroundColor, backgroundColor);
		this.drawGradientRect(actualX - 4, actualY - 3, actualX - 3, actualY + tooltipHeight + 3, backgroundColor, backgroundColor);
		this.drawGradientRect(actualX + stringWidth + 3, actualY - 3, actualX + stringWidth + 4, actualY + tooltipHeight + 3, backgroundColor, backgroundColor);
		int gradientBegin = 0x505000ff;
		int gradientEnd = (gradientBegin & 0xfefefe) >> 1 | gradientBegin & 0xff000000;
		this.drawGradientRect(actualX - 3, actualY - 3 + 1, actualX - 3 + 1, actualY + tooltipHeight + 3 - 1, gradientBegin, gradientEnd);
		this.drawGradientRect(actualX + stringWidth + 2, actualY - 3 + 1, actualX + stringWidth + 3, actualY + tooltipHeight + 3 - 1, gradientBegin, gradientEnd);
		this.drawGradientRect(actualX - 3, actualY - 3, actualX + stringWidth + 3, actualY - 3 + 1, gradientBegin, gradientBegin);
		this.drawGradientRect(actualX - 3, actualY + tooltipHeight + 2, actualX + stringWidth + 3, actualY + tooltipHeight + 3, gradientEnd, gradientEnd);

		mc.fontRenderer.drawStringWithShadow(displayString, actualX, actualY, 0xffffffff);

		this.zLevel = 0.0F;
	}
}