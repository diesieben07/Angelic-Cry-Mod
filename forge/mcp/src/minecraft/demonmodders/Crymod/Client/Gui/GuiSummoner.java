package demonmodders.Crymod.Client.Gui;

import static demonmodders.Crymod.Common.Gui.ContainerSummoner.BUTTON_NEXT_PAGE;
import static demonmodders.Crymod.Common.Gui.ContainerSummoner.BUTTON_PREV_PAGE;
import static demonmodders.Crymod.Common.Gui.ContainerSummoner.BUTTON_SUMMON;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

import demonmodders.Crymod.Common.Gui.ContainerSummoner;
import demonmodders.Crymod.Common.Inventory.InventorySummoner;
import demonmodders.Crymod.Common.Recipes.SummoningEntityList;

public class GuiSummoner extends AbstractGuiContainer<ContainerSummoner, InventorySummoner> {

	private static final int EFFECTIVE_HEIGHT = 256;
	private static final int EFFECTIVE_WIDTH = 176;
	private static final int BOOK_WIDTH = 159;
	private static final int BOOK_HEIGHT = 163;
	private static final int ARROW_HEIGHT = 10;
	private static final int ARROW_WIDTH = 18;

	private static final int HEADING_TEXT_FIELD_X_POSITION = 39;
	private static final int HEADING_TEXT_FIELD_Y_POSITION = 17;
	
	private final String texture;
	
	public GuiSummoner(String texture, ContainerSummoner container) {
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
		
		controlList.add(new GuiButtonImage(BUTTON_NEXT_PAGE, buttonNextX, buttonY, ARROW_WIDTH, ARROW_HEIGHT, 256 - ARROW_WIDTH, 0, texture));
		controlList.add(new GuiButtonImage(BUTTON_PREV_PAGE, buttonPrevX, buttonY, ARROW_WIDTH, ARROW_HEIGHT, 256 - ARROW_WIDTH - ARROW_WIDTH, 0, texture));
		controlList.add(new GuiButton(BUTTON_SUMMON, width / 2 - EFFECTIVE_WIDTH / 2 + 49, height / 2 - EFFECTIVE_HEIGHT / 2 + 150, 80, 20, "Summon"));
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
		String demonName = SummoningEntityList.getSummonings(container.getInventoryInstance().getShowAngels()).get(container.page()).getDemonName();
		int demonNameWidth = fontRenderer.getStringWidth(demonName);
		fontRenderer.drawString(demonName, HEADING_TEXT_FIELD_X_POSITION, HEADING_TEXT_FIELD_Y_POSITION, 0xffffff);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
		if (button.id == BUTTON_SUMMON) {
			mc.displayGuiScreen(null);
		}
	}
}