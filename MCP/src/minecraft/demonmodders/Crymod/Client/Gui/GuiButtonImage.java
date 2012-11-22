package demonmodders.Crymod.Client.Gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;

public class GuiButtonImage extends GuiButton {

	private final int textureX;
	private final int textureY;
	private final String texture;
	
	public GuiButtonImage(int buttonId, int x, int y, int width, int height, int textureX, int textureY, String texture) {
		super(buttonId, x, y, width, height, "");
		this.textureX = textureX;
		this.textureY = textureY;
		this.texture = texture;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.drawButton) {
			boolean isMouseOver = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(mc.renderEngine.getTexture(texture));
			drawTexturedModalRect(xPosition, yPosition, textureX, textureY + (isMouseOver ? height : 0), width, height);
		}
	}

	
	
}
