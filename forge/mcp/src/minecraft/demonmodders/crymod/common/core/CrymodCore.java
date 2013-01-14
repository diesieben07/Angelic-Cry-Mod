package demonmodders.crymod.common.core;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class CrymodCore implements IFMLLoadingPlugin {

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {"demonmodders.crymod.common.core.CrymodTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return "demonmodders.crymod.common.core.CrymodModContainer";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}
}