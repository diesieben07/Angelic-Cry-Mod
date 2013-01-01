package demonmodders.crymod.client.gui.entityinfo;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonEntityInfoRename extends GuiButton {

	public GuiButtonEntityInfoRename(int id, int x, int y) {
		super(id, x, y, 16, 16, "");
	}

	@Override
	public void drawButton(Minecraft mc, int par2, int par3) {
		if (drawButton) {
			GL11.glColor3f(1, 1, 1);
			mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/gui/items.png"));
			drawTexturedModalRect(xPosition, yPosition, 11 * 16, 11 * 16, width, height);
		}
	}
}