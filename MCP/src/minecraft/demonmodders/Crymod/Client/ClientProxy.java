package demonmodders.Crymod.Client;

import net.minecraftforge.client.MinecraftForgeClient;
import demonmodders.Crymod.Common.CommonProxy;
import demonmodders.Crymod.Common.Crymod;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		MinecraftForgeClient.preloadTexture(Crymod.TEXTURE_FILE);
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		
	}

}
