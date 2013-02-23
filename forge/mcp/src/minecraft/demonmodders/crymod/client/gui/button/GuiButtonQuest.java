package demonmodders.crymod.client.gui.button;

import org.lwjgl.opengl.GL11;

import demonmodders.crymod.client.gui.GuiQuests;
import demonmodders.crymod.common.quest.Quest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonQuest extends GuiButton {

	private static final float SCALE_TITLE = 0.75F;
	
	private final Quest quest;
	private final GuiQuests gui;
	
	public GuiButtonQuest(int id, int x, int y, Quest quest, GuiQuests gui) {
		super(id, x, y, 110, 18, quest.getTitle());
		this.quest = quest;
		this.gui = gui;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (drawButton) {
			field_82253_i = gui.activeBtn == id || mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			int color = 14737632;

			if (!enabled) {
				color = -6250336;
			} else if (this.field_82253_i) {
				color = 16777120;
			}
			
			int hoverState = getHoverState(field_82253_i);
			mc.renderEngine.bindTexture(mc.renderEngine.getTexture("/demonmodders/crymod/resource/tex/gui.png"));
			GL11.glColor3f(1, 1, 1);
			drawTexturedModalRect(xPosition, yPosition, 0, 88 + hoverState * 18, width / 2, height);
			drawTexturedModalRect(xPosition + width / 2, yPosition, 77 - this.width / 2, 88 + hoverState * 18, width / 2, height);

			int yOffsetType;
			switch (quest.getQuestType()) {
			case EVIL:
				yOffsetType = 10;
				break;
			case GOOD:
				yOffsetType = 22;
				break;
			default:
				yOffsetType = 34;
			}
			
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glPushMatrix();
			
			int xPositionButton = xPosition + width - 9;
			int yPositionButton = yPosition + 3;
			
			GL11.glTranslatef((xPositionButton) * 0.5F, yPositionButton * 0.5F, 0.5F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			drawTexturedModalRect(xPositionButton, yPositionButton, 20, yOffsetType, 12, 12);
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			GL11.glTranslatef(xPosition * (1F - SCALE_TITLE), yPosition * (1F - SCALE_TITLE), 0);
			GL11.glScalef(SCALE_TITLE, SCALE_TITLE, 1);
			
			drawString(mc.fontRenderer, displayString, xPosition + 3, yPosition + 11, color);
			GL11.glPopMatrix();
		}
	}

}