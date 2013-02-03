package demonmodders.crymod.common.core;

import static org.objectweb.asm.Opcodes.RETURN;

import java.io.InputStream;
import java.util.Properties;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

import demonmodders.crymod.common.Crymod;

public class ObfuscationHelper {

	private static final boolean OBF = false;
	public static final Properties mappings = new Properties();

	static {
		InputStream inStream = ObfuscationHelper.class.getResourceAsStream("/demonmodders/crymod/resource/misc/asm_" + (OBF ? "obf" : "deobf") + ".properties");
		try {
			mappings.load(inStream);
		} catch (Throwable t) {
			throw new RuntimeException("Failed to load Class Mappings!", t);
		}
	}
	
	public static void infoHook(String methodName) {
		Crymod.logger.info("Inserted Callback Hook into " + methodName);
	}

	public static void addBeforeReturn(InsnList toAdd, InsnList original) {
		for (int i = 0; i < original.size(); i++) {
			AbstractInsnNode insn = original.get(i);
			if (insn.getOpcode() == RETURN) {
				original.insertBefore(insn, toAdd);
				break;
			}
		}
	}
}