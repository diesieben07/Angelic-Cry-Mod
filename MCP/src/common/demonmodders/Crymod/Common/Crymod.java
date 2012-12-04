package demonmodders.Crymod.Common;

import java.util.Arrays;
import java.util.logging.Logger;

import net.minecraft.src.Entity;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import demonmodders.Crymod.Common.Entities.EntityHeavenZombie;
import demonmodders.Crymod.Common.Entities.EntityHellZombie;
import demonmodders.Crymod.Common.Gui.CrymodGuiHandler;
import demonmodders.Crymod.Common.Items.ItemCryMod;
import demonmodders.Crymod.Common.Karma.KarmaEventHandler;
import demonmodders.Crymod.Common.Karma.PlayerPowersHandler;
import demonmodders.Crymod.Common.Network.CrymodPacket;
import demonmodders.Crymod.Common.Network.CrymodPacketHandler;
import demonmodders.Crymod.Common.PlayerInfo.PlayerInfo;
import demonmodders.Crymod.Common.WorldGen.CryWorldGenerator;

@Mod(modid = "crymod", name = "Angelic Cry Mod [WIP]", version = "0.1")
@NetworkMod(channels = {CrymodPacket.CHANNEL}, packetHandler = CrymodPacketHandler.class, tinyPacketHandler = CrymodPacketHandler.class, clientSideRequired = true, serverSideRequired = false)
public class Crymod {
	
	public static String VERSION;
	
	@SidedProxy(clientSide = "demonmodders.Crymod.Client.ClientProxy", serverSide = "demonmodders.Crymod.Common.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance
	public static Crymod instance;
	
	public static Logger logger;
	
	public static Configuration conf;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent evt) {
		logger = Logger.getLogger("Crymod");
		logger.setParent(FMLLog.getLogger());
		logger.setUseParentHandlers(true);
		
		ModMetadata metadata = evt.getModMetadata();
		metadata.autogenerated = false;
		metadata.authorList = Arrays.asList("Resinresin", "diesieben07");
		metadata.description = "This Mod is Work in Progress.";		
		
		VERSION = metadata.version;
		
		logger.info("Crymod Version " + metadata.version + " preInitializing...");
		
		conf = new Configuration(evt.getSuggestedConfigurationFile());
		conf.load();
		
		LanguageLoader.loadLanguages();
		
		NetworkRegistry.instance().registerGuiHandler(this, new CrymodGuiHandler());
		
		proxy.preInit();

		KarmaEventHandler.init();
		PlayerPowersHandler.init();
		PlayerInfo.init();
				
		GameRegistry.registerWorldGenerator(new CryWorldGenerator());
		
		registerEntity(EntityHeavenZombie.class, "crymodHeavenZombie", 0xffffff, 0x000000, 80, 3, true);
		registerEntity(EntityHellZombie.class, "crymodHellZombie", 0x000000, 0xffffff, 80, 3, true);
	}
	
	private void registerEntity(Class<? extends Entity> entity, String name, int foreground, int background, int range, int updateFrequency, boolean velocityUpdates) {
		int entityId = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(entity, name, entityId, foreground, background);
		EntityRegistry.registerModEntity(entity, name, entityId, this, range, updateFrequency, velocityUpdates);
	}
	
	@Init
	public void init(FMLInitializationEvent evt) {
		proxy.init();
		
		ItemCryMod.createItems();
		
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent evt) {
		proxy.postInit();
			
		conf.save();
	}
	
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent evt) {
		evt.registerServerCommand(new CommandKarma());
	}
	
	public static final String TEXTURE_FILE = "/crymodResource/tex/textures.png";
	public static final String PARTICLE_TEXTURE_FILE = "/crymodResource/tex/particles.png";
}
