
package demonmodders.crymod.client.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import demonmodders.crymod.common.gui.ContainerEnderBook;
import demonmodders.crymod.common.inventory.InventoryEnderBook;
import demonmodders.crymod.common.playerinfo.PlayerInformation;
import demonmodders.crymod.common.recipes.SummoningRecipe;

public class GuiEnderBook extends AbstractGuiContainer<ContainerEnderBook, InventoryEnderBook> {

	private static final int ARROW_HEIGHT = 10;
	private static final int ARROW_WIDTH = 18;
	
	public static final int PREV_PAGE = 1;
	public static final int NEXT_PAGE = 0;
	
	public static final int SCROLLBAR_HEIGHT = 40;
	
	private SummoningRecipe[] knownRecipes;
	
	private int scrollbarPosition = 0;
	private int mouseScrollbarDelta = 0;
	private boolean draggingScrollbar = false;
	
	int tickCounter = 0;
	
	public GuiEnderBook(ContainerEnderBook container) {
		super(container);
		xSize = 146;
		ySize = 180;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		knownRecipes = PlayerInformation.forPlayer(mc.thePlayer).getEnderBookRecipes();
		int buttonYPos = (height - ySize) / 2 + ySize - ARROW_HEIGHT - 20;
		controlList.add(new GuiButtonImage(GuiEnderBook.NEXT_PAGE, (width - xSize) / 2 + xSize - ARROW_WIDTH - 20, buttonYPos, ARROW_WIDTH, ARROW_HEIGHT, 256 - ARROW_WIDTH, 0, "/demonmodders/crymod/resource/tex/enderBook.png"));
		controlList.add(new GuiButtonImage(GuiEnderBook.PREV_PAGE, (width - xSize) / 2 + 20, buttonYPos, ARROW_WIDTH, ARROW_HEIGHT, 256 - (ARROW_WIDTH * 2), 0, "/demonmodders/crymod/resource/tex/enderBook.png"));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int scaledMouseX, int scaledMouseY) {
		// scrollbar handling
		GL11.glColor3f(1, 1, 1);
		RenderHelper.disableStandardItemLighting();
		
		if (draggingScrollbar) {
			scrollbarPosition = scaledMouseY - (height - ySize) / 2 - 4 - mouseScrollbarDelta;
			if (scrollbarPosition > (ySize - SCROLLBAR_HEIGHT - 3)) {
				scrollbarPosition = ySize - SCROLLBAR_HEIGHT - 3;
			}
			
			if (scrollbarPosition < 3) {
				scrollbarPosition = 3;
			}
		}
		
		if (container.currentRecipe != null) {
			fontRenderer.drawString(container.currentRecipe.getRecipeName(), 26, 20, 0x000000);
		}
		
		int movedScrollbarPosition = scrollbarPosition - 3;
		int length = knownRecipes.length - 12;
		if (length < 0) {
			length = 0;
		}
		int listOffset = (int) ((float)movedScrollbarPosition / (float)(ySize - SCROLLBAR_HEIGHT - 6)) * length;
		int y = 4;
		for (int i = listOffset; (i < knownRecipes.length && i < listOffset + 13); i++) {
			String recipeName = knownRecipes[i].getRecipeName();
			fontRenderer.drawString(recipeName, - (width - xSize) / 2 + 5, y, 0xFFFAFA);
			if (y == ySize - 11) {
				break;
			}
			y += 15;
		}
		
		// scrollbar
		drawRect(-8, scrollbarPosition + 2, -1, scrollbarPosition + SCROLLBAR_HEIGHT + 1, 0xff212121);
		drawRect(-7, scrollbarPosition + 3, -2, scrollbarPosition + SCROLLBAR_HEIGHT, 0xff414141);
	}

	@Override
	protected String getTextureFile() {
		return "/demonmodders/crymod/resource/tex/enderBook.png";
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case GuiEnderBook.NEXT_PAGE:
			container.setActivePage(container.currentPage + 1);
			break;
		case GuiEnderBook.PREV_PAGE:
			container.setActivePage(container.currentPage - 1);
			break;
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		if (button == 0 && isPointInRegion(-7, scrollbarPosition + 3, 5, SCROLLBAR_HEIGHT - 2, x, y)) {
			int modifiedMouseY = y - (height - ySize) / 2 - 4;
			mouseScrollbarDelta = modifiedMouseY - scrollbarPosition;
			draggingScrollbar = true;
		}
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int which) {
		super.mouseMovedOrUp(x, y, which);
		if (which == 0) {
			draggingScrollbar = false;
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		tickCounter++;
		if (tickCounter == 20) {
			tickCounter = 0;
			container.updateScrollingSlot();
		}
	}
}