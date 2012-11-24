package demonmodders.Crymod.Common;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.src.CallableMinecraftVersion;

import cpw.mods.fml.common.versioning.ComparableVersion;

public class UpdateChecker implements Runnable {	
	
	private static State state = State.DISABLED;
	private static final Object lock = new Object();
	
	private final URL versionInfo;
	
	private UpdateChecker(URL versionInfo) {
		this.versionInfo = versionInfo;
	}
	
	@Override
	public void run() {
		try {
			
			final String thisMinecraftVersion = new CallableMinecraftVersion(null).minecraftVersion();
			final ComparableVersion thisModVersion = new ComparableVersion(Crymod.VERSION);
			
			InputStream versionInfoStream = versionInfo.openStream();
			PropertyReader reader = new PropertyReader(versionInfoStream);
			reader.read();
			ComparableVersion newestVersion = new ComparableVersion(Crymod.VERSION);
			String minecraftVersionsForNewest = null;
			for (Entry<String, String> entry : reader.getEntrySet()) {
				ComparableVersion currentCheckVersion = new ComparableVersion(entry.getKey());
				if (currentCheckVersion.compareTo(newestVersion) > 0) {
					newestVersion = currentCheckVersion;
					minecraftVersionsForNewest = entry.getValue();
				}
			}
			if (newestVersion.compareTo(thisModVersion) > 0) {
				// outdated
				List<String> minecraftVersionsForNewestParsed = Arrays.<String>asList(minecraftVersionsForNewest.split(";"));
				if (minecraftVersionsForNewestParsed.contains(thisMinecraftVersion)) {
					setState(State.OUTDATED);
				} else {
					setState(State.OUTDATED_CANNOT_UPDATE);
				}
			} else {
				setState(State.UP_TO_DATE);
			}
		} catch (Exception e) {
			setState(State.ERRORED);
		}
	}
	
	public static void setState(State state) {
		synchronized (lock) {
			UpdateChecker.state = state;
		}
	}
	
	public static State getState() {
		synchronized (lock) {
			return state;
		}
	}
	
	public static enum State {
		DISABLED, IN_PROGRESS, UP_TO_DATE, OUTDATED, OUTDATED_CANNOT_UPDATE, ERRORED
	}
	
	public static void startCheck() {
		new Thread(new UpdateChecker(Crymod.UPDATE_URL), "SummoningMod Update Thread").start();
	}
}