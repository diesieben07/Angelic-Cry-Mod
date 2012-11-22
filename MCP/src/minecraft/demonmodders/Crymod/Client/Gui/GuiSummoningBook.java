package demonmodders.Crymod.Client.Gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.Container;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;

public class GuiSummoningBook extends GuiContainer {

	private static final int EFFECTIVE_HEIGHT = 256;
	private static final int EFFECTIVE_WIDTH = 176;
	private static final int BOOK_WIDTH = 159;
	private static final int BOOK_HEIGHT = 163;
	private static final int ARROW_HEIGHT = 10;
	private static final int ARROW_WIDTH = 18;
	
	private static final int BUTTON_NEXT = 0;
	private static final int BUTTON_PREV = 1;
	
	private final String texture;
	
	public GuiSummoningBook(String texture, Container container) {
		super(container);
		this.texture = texture;
	}

	@Override
	public void initGui() {
		super.initGui();
		int buttonNextX = (width - EFFECTIVE_WIDTH) / 2 + BOOK_WIDTH + 3;
		int buttonPrevX = (width - EFFECTIVE_WIDTH) / 2 - 3;
		
		int buttonY = (height - EFFECTIVE_HEIGHT) / 2 + BOOK_HEIGHT - ARROW_HEIGHT - 3;
		
		controlList.add(new GuiButtonImage(BUTTON_NEXT, buttonNextX, buttonY, ARROW_WIDTH, ARROW_HEIGHT, 256 - ARROW_WIDTH, 0, texture));
		controlList.add(new GuiButtonImage(BUTTON_PREV, buttonPrevX, buttonY, ARROW_WIDTH, ARROW_HEIGHT, 256 - ARROW_WIDTH - ARROW_WIDTH, 0, texture));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case BUTTON_NEXT:
			break;
		case BUTTON_PREV:
			break;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int textureName = mc.renderEngine.getTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(textureName);
        drawTexturedModalRect(width / 2 - EFFECTIVE_WIDTH / 2, height / 2 - EFFECTIVE_HEIGHT / 2, 0, 0, EFFECTIVE_WIDTH, EFFECTIVE_HEIGHT);
	}

}
