package demonmodders.crymod.common.core.transformers;

import static demonmodders.crymod.common.core.ObfuscationHelper.mappings;
import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import cpw.mods.fml.relauncher.IClassTransformer;
import demonmodders.crymod.common.Crymod;

public abstract class AbstractClassTransformer implements IClassTransformer, Opcodes {

	private final String clazzName;
	private List<FieldNode> fieldsToAdd;
	
	public AbstractClassTransformer(String clazzNameProperty) {
		clazzName = mappings.getProperty(clazzNameProperty);
	}

	@Override
	public final byte[] transform(String name, byte[] bytes) {
		if (name.equals(clazzName)) {
			ClassNode clazz = new ClassNode();
			ClassReader reader = new ClassReader(bytes);
			reader.accept(clazz, 0);
			
			boolean hasTransformed = false;
			
			if (fieldsToAdd != null) {
				hasTransformed = true;
				for (FieldNode field : fieldsToAdd) {
					clazz.fields.add(field);
				}
			}
			
			for (MethodNode method : (List<MethodNode>)clazz.methods) {
				hasTransformed = transformMethod(method.name + method.desc, method) || hasTransformed;
			}
			
			if (hasTransformed) {
				ClassWriter writer = new ClassWriter(COMPUTE_MAXS | COMPUTE_FRAMES);
				clazz.accept(writer);
				return writer.toByteArray();
			} else {
				return bytes;
			}
		} else {
			return bytes;
		}
	}
	
	protected final void infoHook(String methodName) {
		Crymod.logger.info("Inserted Callback Hook into " + methodName);
	}

	protected final void insertHookEnd(InsnList hook, MethodNode method) {
		insertHookEnd(hook, method.instructions);
	}
	
	protected final void insertHookEnd(InsnList hook, InsnList original) {
		for (int i = 0; i < original.size(); i++) {
			AbstractInsnNode insn = original.get(i);
			if (insn.getOpcode() == RETURN) {
				original.insertBefore(insn, hook);
				break;
			}
		}
	}
	
	private static final List<String> primitives = Arrays.asList("V", "Z", "C", "B", "S", "I", "F", "D", "L");
	
	protected final AbstractInsnNode makeStaticCall(Class<?> targetClazz, String method, String... params) {
		return makeStaticCall(targetClazz.getCanonicalName(), method, params);
	}
	
	protected final AbstractInsnNode makeStaticCall(String targetClazz, String method, String... params) {
		StringBuilder desc = new StringBuilder();
		desc.append("(");
		for (String param : params) {
			if (!primitives.contains(param)) {
				desc.append("L").append(param.replace('.', '/')).append(";");
			} else {
				desc.append(param);
			}
		}
		desc.append(")V");
		return new MethodInsnNode(INVOKESTATIC, targetClazz.replace('.', '/'), method, desc.toString());
	}
	
	protected final void addField(String name, Class<?> typeClazz) {
		addField(name, typeClazz.getCanonicalName());
	}
	
	protected final void addField(String name, String typeClazz) {
		if (fieldsToAdd == null) {
			fieldsToAdd = new ArrayList();
		}
		String desc;
		if (primitives.contains(typeClazz)) {
			desc = typeClazz;
		} else {
			desc = "L" + typeClazz.replace('.', '/') + ";";
		}
		fieldsToAdd.add(new FieldNode(ACC_PUBLIC, name, desc, null, null));
	}

	protected abstract boolean transformMethod(String methodNameDescr, MethodNode method);
}