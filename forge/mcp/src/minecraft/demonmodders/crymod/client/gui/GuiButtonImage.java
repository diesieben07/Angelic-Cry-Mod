package demonmodders.crymod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

public class GuiButtonImage extends GuiButton {

	private final int textureX;
	private final int textureY;
	private final String texture;
	
	public GuiButtonImage(int buttonId, int x, int y, int width, int height, int textureX, int textureY, String texture) {
		super(buttonId, x, y, width, height, null);
		this.textureX = textureX;
		this.textureY = textureY;
		this.texture = texture;
	}
	
	public GuiButtonImage setDisplayString(String displayString) {
		this.displayString = displayString;
		return this;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.drawButton) {
			boolean isMouseOver = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(mc.renderEngine.getTexture(texture));
			drawTexturedModalRect(xPosition, yPosition, textureX, textureY + (isMouseOver ? height : 0), width, height);
			
			if (displayString != null) {
				drawCenteredString(mc.fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, isMouseOver ? 0xffffa0 : 0xffffff);
			}
		}
	}

	
	
}
