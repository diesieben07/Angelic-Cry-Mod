package demonmodders.Crymod.Client.Gui;

import org.lwjgl.opengl.GL11;

import demonmodders.Crymod.Common.Inventory.ContainerSummoner;
import demonmodders.Crymod.Common.Recipes.SummoningRecipeRegistry;

import net.minecraft.src.Container;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.Slot;

public class GuiSummoner extends GuiContainer {

	private static final int EFFECTIVE_HEIGHT = 256;
	private static final int EFFECTIVE_WIDTH = 176;
	private static final int BOOK_WIDTH = 159;
	private static final int BOOK_HEIGHT = 163;
	private static final int ARROW_HEIGHT = 10;
	private static final int ARROW_WIDTH = 18;

	private static final int HEADING_TEXT_FIELD_X_POSITION = 39;
	private static final int HEADING_TEXT_FIELD_Y_POSITION = 17;
	
	private static final int BUTTON_NEXT = 0;
	private static final int BUTTON_PREV = 1;
	
	private final String texture;
	
	public GuiSummoner(String texture, Container container) {
		super(container);
		this.texture = texture;
		xSize = EFFECTIVE_WIDTH;
		ySize = EFFECTIVE_HEIGHT;
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
			((ContainerSummoner)inventorySlots).nextPage();
			break;
		case BUTTON_PREV:
			((ContainerSummoner)inventorySlots).prevPage();
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

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String demonName = SummoningRecipeRegistry.getRecipes().get(((ContainerSummoner)inventorySlots).page()).getDemon();
		int demonNameWidth = fontRenderer.getStringWidth(demonName);
		fontRenderer.drawString(demonName, HEADING_TEXT_FIELD_X_POSITION, HEADING_TEXT_FIELD_Y_POSITION, 0xffffff);
	}
}
