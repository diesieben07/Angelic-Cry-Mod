package demonmodders.Crymod.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import demonmodders.Crymod.Client.FX.EntitySummonFX;
import demonmodders.Crymod.Client.Gui.GuiCrystalBag;
import demonmodders.Crymod.Client.Gui.GuiRechargeStation;
import demonmodders.Crymod.Client.Gui.GuiSummoner;
import demonmodders.Crymod.Common.CommonProxy;
import demonmodders.Crymod.Common.Crymod;
import demonmodders.Crymod.Common.Entities.ZombieBase;
import demonmodders.Crymod.Common.Gui.ContainerCrystalBag;
import demonmodders.Crymod.Common.Gui.ContainerRechargeStation;
import demonmodders.Crymod.Common.Gui.ContainerSummoner;
import demonmodders.Crymod.Common.Gui.GuiType;
import demonmodders.Crymod.Common.Items.ItemCryMod;
import demonmodders.Crymod.Common.Network.PacketClientEffect;
import demonmodders.Crymod.Common.PlayerInfo.PlayerInfo;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void setClientPlayerInfo(PlayerInfo info) {
		ClientTickHandler.instance().setClientPlayerInfo(info);
	}

	@Override
	public void handleClientEffect(PacketClientEffect effect) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		
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
		}
	}

	@Override
	public Object getClientGuiElement(Container container, int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (GuiType.fromGuiId(id)) {
		case SUMMONING_BOOK:
			return new GuiSummoner("/crymodResource/tex/summoningBook.png", (ContainerSummoner)container);
		case EVIL_TABLET:
			return new GuiSummoner("/crymodResource/tex/evilTablet.png", (ContainerSummoner)container);
		case RECHARGE_STATION:
			return new GuiRechargeStation((ContainerRechargeStation)container);
		case CRYSTAL_BAG:
			return new GuiCrystalBag((ContainerCrystalBag)container);
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
	}

	@Override
	public void init() {
		MinecraftForgeClient.registerItemRenderer(ItemCryMod.crystal.shiftedIndex, new CrymodItemRenderer());
	}

	@Override
	public void postInit() {
		
	}

}
