package demonmodders.Crymod.Client.Gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.Container;
import net.minecraft.src.GuiContainer;

public class GuiSummoningBook extends GuiContainer {

	private static final int EFFECTIVE_HEIGHT = 256;
	private static final int EFFECTIVE_WIDTH = 176;
	
	private final String texture;
	
	public GuiSummoningBook(String texture, Container container) {
		super(container);
		this.texture = texture;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int texture = mc.renderEngine.getTexture(this.texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(width / 2 - EFFECTIVE_WIDTH / 2, height / 2 - EFFECTIVE_HEIGHT / 2, 0, 0, EFFECTIVE_WIDTH, EFFECTIVE_HEIGHT);
	}

}
