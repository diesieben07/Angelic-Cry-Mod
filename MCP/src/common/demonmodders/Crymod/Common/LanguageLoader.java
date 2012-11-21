package demonmodders.Crymod.Common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraftforge.common.Configuration;
import static net.minecraftforge.common.Configuration.CATEGORY_GENERAL;

import static demonmodders.Crymod.Common.Crymod.conf;
import static demonmodders.Crymod.Common.Crymod.logger;

public class LanguageLoader {

	private static final String DEFAULT_LANGUAGE = "en";
	private static final String SEPARATOR = ":";
	private static final String COMMENT = "#";
	
	public static void loadLanguages() {
		String language = conf.get(CATEGORY_GENERAL, "language", DEFAULT_LANGUAGE).value;
		InputStream stream = LanguageLoader.class.getResourceAsStream("/crymodResource/lang/" + language + ".lang");
		if (stream == null) {
			logger.warning("Language " + language + " not found. Using default.");
			stream = LanguageLoader.class.getResourceAsStream("/crymodResource/lang/" + DEFAULT_LANGUAGE + ".lang");
		}
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = reader.readLine()) != null) {
				int colonIndex = line.indexOf(SEPARATOR);
				if (colonIndex > 0 && !line.startsWith(COMMENT)) {
					String key = line.substring(0, colonIndex);
					String value = line.substring(colonIndex + COMMENT.length());
					LanguageRegistry.instance().addStringLocalization(key, value);
					System.out.println(key + ": " + value);
				}
			}
		} catch (Exception e) {
			logger.warning("Failed to load any language.");
		} finally {
			try {
				reader.close();
			} catch (Exception e) {}
		}
	}
}