package demonmodders.crymod.client;

import java.io.File;
import java.util.List;
import java.util.Random;

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
import demonmodders.crymod.client.fx.EntityFXTextureChange;
import demonmodders.crymod.client.gui.GuiCrystalBag;
import demonmodders.crymod.client.gui.GuiEnderBook;
import demonmodders.crymod.client.gui.GuiMagiciser;
import demonmodders.crymod.client.gui.GuiQuestBook;
import demonmodders.crymod.client.gui.GuiQuests;
import demonmodders.crymod.client.gui.GuiRechargeStation;
import demonmodders.crymod.client.gui.GuiRecipePage;
import demonmodders.crymod.client.gui.GuiSummoner;
import demonmodders.crymod.client.gui.entityinfo.GuiEntityInfo;
import demonmodders.crymod.client.gui.updates.GuiServerUpdates;
import demonmodders.crymod.client.render.CrymodItemRenderer;
import demonmodders.crymod.client.render.RenderEnderBook;
import demonmodders.crymod.client.render.RenderZombieBase;
import demonmodders.crymod.common.CrymodProxy;
import demonmodders.crymod.common.CrymodUtils;
import demonmodders.crymod.common.UpdateChecker.UpdateStatus;
import demonmodders.crymod.common.container.ContainerCrystalBag;
import demonmodders.crymod.common.container.ContainerEnderBook;
import demonmodders.crymod.common.container.ContainerEntityInfo;
import demonmodders.crymod.common.container.ContainerMagiciser;
import demonmodders.crymod.common.container.ContainerQuests;
import demonmodders.crymod.common.container.ContainerRechargeStation;
import demonmodders.crymod.common.container.ContainerRecipePage;
import demonmodders.crymod.common.container.ContainerSummoner;
import demonmodders.crymod.common.container.GuiType;
import demonmodders.crymod.common.entities.EntityQuester;
import demonmodders.crymod.common.entities.ZombieBase;
import demonmodders.crymod.common.items.ItemCryMod;
import demonmodders.crymod.common.network.PacketClientAction;
import demonmodders.crymod.common.network.PacketClientEffect;
import demonmodders.crymod.common.network.PacketClientEffect.Type;
import demonmodders.crymod.common.network.PacketUpdateInformation;
import demonmodders.crymod.common.recipes.SummoningRecipe;
import demonmodders.crymod.common.tileentities.TileEntityEnderbook;

public class ClientProxy implements CrymodProxy {
	
	private final Minecraft mc = Minecraft.getMinecraft();
	private final Random random = new Random();
	private UpdateStatus lastStatus = null;
	private List<String> lastUpdateInformation = null;
	
	@Override
	public void handleClientEffect(PacketClientEffect effect) {
		switch (effect.type) {
		case SUMMON_BAD:
		case SUMMON_GOOD:
			for (int i = 0; i < 30; i++) {
				double x = effect.x + random.nextDouble() * (random.nextBoolean() ? -2 : 2);
				double y = effect.y + 1 + random.nextDouble() * 2;
				double z = effect.z + random.nextDouble() * (random.nextBoolean() ? -2 : 2);
				
				int begin = effect.type == Type.SUMMON_BAD ? 16 : 32;
								
				mc.effectRenderer.addEffect(new EntityFXTextureChange(mc.renderEngine, mc.theWorld, x, y, z, 0, 0, 0).setBeginIndex(begin).setEndIndex(begin + 7).setSpeed(3 + random.nextInt(3)).setMaxAge(20 + random.nextInt(20)).makeMovementNoise());
			}
			break;
		case SWORD_OF_DARKNESS:
			for (int i = 0; i < 10; i++) {
				mc.renderGlobal.spawnParticle("largesmoke", effect.x, effect.y, effect.z, 0, 0, 0);
			}
			break;
		case ENDERBOOK:
			for (int i = 0; i < 15; i++) {
				double x = effect.x + random.nextDouble() * (random.nextBoolean() ? -2 : 2);
				double y = effect.y + 1 + random.nextDouble() * 2;
				double z = effect.z + random.nextDouble() * (random.nextBoolean() ? -2 : 2);
				
				mc.effectRenderer.addEffect(new EntityFXTextureChange(mc.renderEngine, mc.theWorld, x, y, z, 0, 0, 0).setBeginIndex(0).setEndIndex(7).setSpeed(1 + random.nextInt(3)));
			}
			break;
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
		case SUMMONED_ENTITY:
			return new GuiEntityInfo((ContainerEntityInfo)container);
		case MAGICISER:
			return new GuiMagiciser((ContainerMagiciser)container);
		case QUESTS:
			return new GuiQuests((ContainerQuests)container);
		default:
			return null;
		}
	}

	@Override
	public void preInit() {
		MinecraftForgeClient.preloadTexture(CrymodUtils.TEXTURE_FILE);
		TickRegistry.registerTickHandler(HudOverlayTicker.instance(), Side.CLIENT);
		TickRegistry.registerTickHandler(new GuiTicker(), Side.CLIENT);
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		KeyBindingRegistry.registerKeyBinding(new CrymodKeyHandler());
		
		RenderingRegistry.registerEntityRenderingHandler(ZombieBase.class, new RenderZombieBase());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderbook.class, new RenderEnderBook());
	}

	@Override
	public void init() {
		MinecraftForgeClient.registerItemRenderer(ItemCryMod.crystal.itemID, new CrymodItemRenderer());
	}

	@Override
	public void postInit() {
		
	}

	@Override
	public File getMinecraftDir() {
		return mc.mcDataDir;
	}

	@Override
	public void handleUpdateInformation(PacketUpdateInformation packet) {
		lastStatus = packet.getStatus();
		lastUpdateInformation = packet.getUpdateInformation();
		if (mc.currentScreen != null && mc.currentScreen instanceof GuiServerUpdates) {
			((GuiServerUpdates)mc.currentScreen).handleStatus(lastStatus, lastUpdateInformation);
		}
	}
	
	@Override
	public void handleClientAction(PacketClientAction packet) {
		switch (packet.getAction()) {
		case OPEN_UPDATES:
			GuiServerUpdates screen = new GuiServerUpdates();
			mc.displayGuiScreen(screen);
			screen.handleStatus(lastStatus, lastUpdateInformation);
			break;
		}
	}

	@Override
	public void openQuestBook() {
		mc.displayGuiScreen(new GuiQuestBook());
	}
}