package demonmodders.crymod.common.core.transformers;

import static demonmodders.crymod.common.core.ObfuscationHelper.mappings;
import static org.objectweb.asm.Opcodes.ALOAD;

import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import demonmodders.crymod.common.handlers.event.KarmaEventHandler;

@TransformerExclusions({ "demonmodders.", "fml.", "net.minecraftforge." })
public class EntityAITransformer extends AbstractClassTransformer {

	public EntityAITransformer() {
		super("EntityAIMate");
	}

	@Override
	protected boolean transformMethod(String nameDescr, MethodNode method) {
		if (nameDescr.equals(mappings.getProperty("EntityAIMate.spawnBaby"))) {
			InsnList hook = new InsnList();
			hook.add(new VarInsnNode(ALOAD, 1)); // var1: the baby
			hook.add(new VarInsnNode(ALOAD, 0)); // this
			hook.add(makeStaticCall(KarmaEventHandler.class, "onBreedingSpawnChild", mappings.getProperty("EntityAgeable"), mappings.getProperty("EntityAIMate")));
			
			insertHookEnd(hook, method);
			infoHook("EntityAIMate/spawnBaby");
			return true;
		}
		
		return false;
	}

}
