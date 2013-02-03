package demonmodders.crymod.common.core.transformers;

import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import static demonmodders.crymod.common.core.ObfuscationHelper.mappings;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import demonmodders.crymod.common.karma.KarmaEventHandler;

@TransformerExclusions({ "demonmodders.", "fml.", "net.minecraftforge." })
public class ZombieTransformer extends AbstractClassTransformer {

	public ZombieTransformer() {
		super("EntityZombie");
	}

	@Override
	protected boolean transformMethod(String nameDescr, MethodNode method) {
		if (nameDescr.equals(mappings.getProperty("EntityZombie.convertToVillager"))) {
			
			InsnList hook = new InsnList();
			hook.add(new VarInsnNode(ALOAD, 0)); // this: the zombie
			hook.add(makeStaticCall(KarmaEventHandler.class.getCanonicalName(), "onZombieConvert", mappings.getProperty("EntityZombie")));
			insertHookEnd(hook, method);
			
			infoHook("EntityZombie/convertToVillager");
			
			return true;
		} else {
			return false;
		}
	}

}
