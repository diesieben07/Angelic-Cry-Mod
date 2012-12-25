package demonmodders.crymod.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import demonmodders.crymod.common.items.ItemCrystal;

public class CrymodItemRenderer implements IItemRenderer {

	private final RenderItem itemRenderer = new RenderItem();
	private final Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        int texture = item.getIconIndex();
		// first render the item
        itemRenderer.renderTexturedQuad(0, 0, texture % 16 * 16, texture / 16 * 16, 16, 16);
        // and now our custom "damage" bar
                
        int reversedCharge = ItemCrystal.MAX_CHARGE - ItemCrystal.getCharge(item);
        
        if (reversedCharge == ItemCrystal.MAX_CHARGE) {
        	return;
        }
        int var11 = Math.round(13 - reversedCharge * 13 / ItemCrystal.MAX_CHARGE);
        int var7 = Math.round(255 - reversedCharge * 255 / ItemCrystal.MAX_CHARGE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        int color1 = 255 - var7 << 16 | var7 << 8;
        int color2 = (255 - var7) / 4 << 16 | 16128;
        renderQuad(Tessellator.instance, 2, 13, 13, 2, 0);
        renderQuad(Tessellator.instance, 2, 13, 12, 1, color2);
        renderQuad(Tessellator.instance, 2, 13, var11, 1, color1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	private void renderQuad(Tessellator tesselator, int x, int y, int u, int v, int color) {
        tesselator.startDrawingQuads();
        tesselator.setColorOpaque_I(color);
        tesselator.addVertex(x + 0, y + 0, 0);
        tesselator.addVertex(x + 0, y + v, 0);
        tesselator.addVertex(x + u, y + v, 0);
        tesselator.addVertex(x + u, y + 0, 0);
        tesselator.draw();
    }
}