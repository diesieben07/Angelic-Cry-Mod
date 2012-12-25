package demonmodders.crymod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import demonmodders.crymod.client.fx.EntitySummonFX;
import demonmodders.crymod.client.gui.GuiCrystalBag;
import demonmodders.crymod.client.gui.GuiEnderBook;
import demonmodders.crymod.client.gui.GuiRechargeStation;
import demonmodders.crymod.client.gui.GuiRecipePage;
import demonmodders.crymod.client.gui.GuiSummoner;
import demonmodders.crymod.client.render.CrymodItemRenderer;
import demonmodders.crymod.client.render.RenderEnderBook;
import demonmodders.crymod.client.render.RenderZombieBase;
import demonmodders.crymod.common.CommonProxy;
import demonmodders.crymod.common.Crymod;
import demonmodders.crymod.common.entities.ZombieBase;
import demonmodders.crymod.common.gui.ContainerCrystalBag;
import demonmodders.crymod.common.gui.ContainerEnderBook;
import demonmodders.crymod.common.gui.ContainerRechargeStation;
import demonmodders.crymod.common.gui.ContainerRecipePage;
import demonmodders.crymod.common.gui.ContainerSummoner;
import demonmodders.crymod.common.gui.GuiType;
import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.network.PacketClientEffect;
import demonmodders.crymod.common.network.PacketEnderBookRecipe;
import demonmodders.crymod.common.playerinfo.PlayerInfo;
import demonmodders.crymod.common.recipes.SummoningRecipe;
import demonmodders.crymod.common.tileentities.TileEntityEnderbook;

public class ClientProxy extends CommonProxy {
	
	private final Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void setClientPlayerInfo(PlayerInfo info) {
		ClientTickHandler.instance().setClientPlayerInfo(info);
	}

	@Override
	public void handleClientEffect(PacketClientEffect effect) {
		switch (effect.type) {
		case SUMMON_GOOD:
			double movement = 0.05;
			double movementUp = 0.4;
			mc.effectRenderer.addEffect(new EntitySummonFX(mc.renderEngine, mc.theWorld, effect.x, effect.y, effect.z, movement, movementUp, 0));
			mc.effectRenderer.addEffect(new EntitySummonFX(mc.renderEngine, mc.theWorld, effect.x, effect.y, effect.z, -movement, movementUp, 0));
			mc.effectRenderer.addEffect(new EntitySummonFX(mc.renderEngine, mc.theWorld, effect.x, effect.y, effect.z, 0, movementUp, movement));
			mc.effectRenderer.addEffect(new EntitySummonFX(mc.renderEngine, mc.theWorld, effect.x, effect.y, effect.z, 0, movementUp, -movement));
			mc.effectRenderer.addEffect(new EntitySummonFX(mc.renderEngine, mc.theWorld, effect.x, effect.y, effect.z, 0, movementUp, 0));
			break;
		case SUMMON_BAD:
			break;
		case SWORD_OF_DARKNESS:
			for (int i = 0; i < 10; i++) {
				mc.renderGlobal.spawnParticle("largesmoke", effect.x, effect.y, effect.z, 0, 0, 0);
			}
			break;
		case ENDERBOOK:
			mc.renderGlobal.spawnParticle("largesmoke", effect.x, effect.y + 1, effect.z, 0, 0, 0);
			break;
		}
	}

	@Override
	public void handleEnderBookRecipe(PacketEnderBookRecipe packet) {
		if (mc.currentScreen instanceof GuiEnderBook && mc.thePlayer.openContainer instanceof ContainerEnderBook && mc.thePlayer.openContainer.windowId == packet.windowId) {
			SummoningRecipe recipe = SummoningRecipe.byId(packet.recipeId);
			if (recipe != null) {
				((GuiEnderBook)mc.currentScreen).setCurrentRecipe(recipe);
			}
		}
	}

	@Override
	public Object getClientGuiElement(Container container, int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (GuiType.fromGuiId(id)) {
		case SUMMONING_BOOK:
			return new GuiSummoner("/demonmodders/crymod/resource/tex/summoningBook.png", (ContainerSummoner)container);
		case EVIL_TABLET:
			return new GuiSummoner("/demonmodders/crymod/resource/tex/evilTablet.png", (ContainerSummoner)container);
		case RECHARGE_STATION:
			return new GuiRechargeStation((ContainerRechargeStation)container);
		case CRYSTAL_BAG:
			return new GuiCrystalBag((ContainerCrystalBag)container);
		case ENDER_BOOK:
			return new GuiEnderBook((ContainerEnderBook)container);
		case RECIPE_PAGE:
			return new GuiRecipePage((ContainerRecipePage)container);
		default:
			return null;
		}
	}

	@Override
	public void preInit() {
		MinecraftForgeClient.preloadTexture(Crymod.TEXTURE_FILE);
		TickRegistry.registerTickHandler(ClientTickHandler.instance(), Side.CLIENT);
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		KeyBindingRegistry.registerKeyBinding(new CrymodKeyHandler());
		
		RenderingRegistry.registerEntityRenderingHandler(ZombieBase.class, new RenderZombieBase());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderbook.class, new RenderEnderBook());
	}

	@Override
	public void init() {
		MinecraftForgeClient.registerItemRenderer(ItemCryMod.crystal.shiftedIndex, new CrymodItemRenderer());
	}

	@Override
	public void postInit() {
		
	}

}
