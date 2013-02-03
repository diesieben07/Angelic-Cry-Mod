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
		return new String[] {
				"demonmodders.crymod.common.core.transformers.ZombieTransformer",
				"demonmodders.crymod.common.core.transformers.PlayerTransformer",
				"demonmodders.crymod.common.core.transformers.EntityAITransformer",
				"demonmodders.crymod.common.core.transformers.BlockTransformer",
				"demonmodders.crymod.common.core.transformers.ItemStackTransformer"
		};
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