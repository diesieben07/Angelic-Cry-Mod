package demonmodders.crymod.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.crash.CallableMinecraftVersion;
import net.minecraft.util.StringTranslate;
import net.minecraftforge.common.Configuration;

public class UpdateChecker implements Runnable {

	public static final String VERSION = "0.1";
	
	private static final String UPDATE_URL = "http://www.take-weiland.de/summoningmod/versions.php";

	public static final String MINECRAFT_VERSION = new CallableMinecraftVersion(null).minecraftVersion();
	
	private UpdateStatus currentStatus;
	
	private Thread currentThread = null;
	
	private Object downloadInfoLock = new Object();
	
	private String[] currentDownloadedInfo = null;
	
	private final Map<UpdateStatusHandler,Void> handlers = new WeakHashMap();
	
	public UpdateChecker(Configuration conf) {
		postStatus(conf.get(Configuration.CATEGORY_GENERAL, "enableUpdater", true).getBoolean(true) ? UpdateStatus.LOADING : UpdateStatus.DISABLED);
	}
	
	@Override
	public void run() {
		if (currentStatus == UpdateStatus.DISABLED) {
			return;
		}
		postStatus(UpdateStatus.CHECKING);
		Crymod.logger.info("Checking for updates...");
		try {
			final URL updateUrl = new URL(UPDATE_URL);
			final InputStream in = updateUrl.openStream();
			
			final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			boolean isUpToDate = true;
			
			String line;
			String[] splitted = null;
			while ((line = reader.readLine()) != null) {
				splitted = line.split("\\|");
				System.out.println(Arrays.toString(splitted));
				if (splitted.length != 4 || !splitted[0].equals(MINECRAFT_VERSION)) { // don't care if the line is invalid or its not for this version
					System.out.println("dont care");
					continue;
				}
				isUpToDate = splitted[1].equals(VERSION);
				break;
			}
			Crymod.logger.info(isUpToDate ? "Installation is up-to-date." : "Updates are available");
			
			reader.close();
			in.close();
			
			synchronized (downloadInfoLock) {
				currentDownloadedInfo = !isUpToDate ? splitted : null;
			}
			
			postStatus(isUpToDate ? UpdateStatus.UP_TO_DATE : UpdateStatus.UPDATES_AVAILABLE);
		} catch (MalformedURLException e) {
			postStatus(UpdateStatus.FAILED);
			throw new RuntimeException("Unexpected Invalid URL error.", e);
		} catch (IOException e) {
			Crymod.logger.info("Failed to check for updates.");
			e.printStackTrace();
			postStatus(UpdateStatus.FAILED);
		}
		synchronized (this) {
			currentThread = null;
		}
	}
	
	public void registerHandler(UpdateStatusHandler handler) {
		handlers.put(handler, null);
		handler.handleStatus(currentStatus, getUpdateInfoForExternal());
	}
	
	private void postStatus(UpdateStatus status) {
		currentStatus = status;
		List<String> updateInfo = getUpdateInfoForExternal();
		for (UpdateStatusHandler handler : handlers.keySet()) {
			handler.handleStatus(status, updateInfo);
		}
	}
	
	public void startIfNotRunning() {
		synchronized (this) {
			if (currentThread == null) {
				currentThread = new Thread(this);
				currentThread.start();
			}
		}
	}
	
	private List<String> getUpdateInfoForExternal() {
		synchronized (downloadInfoLock) {
			return currentDownloadedInfo == null ? null : Collections.unmodifiableList(new ArrayList(Arrays.asList(currentDownloadedInfo)));
		}
	}
	
	public static enum UpdateStatus {
		LOADING("loading"), CHECKING("checking"), DISABLED("disabled"), FAILED("failed"), UP_TO_DATE("uptodate"), UPDATES_AVAILABLE("available");
		
		private final String langKey;
		
		private UpdateStatus(String langKey) {
			this.langKey = "crymod.ui.updates." + langKey;
		}
		
		public String translate() {
			return StringTranslate.getInstance().translateKey(langKey);
		}
	}
	
	public static interface UpdateStatusHandler {
		
		public void handleStatus(UpdateStatus newStatus, List<String> updateInfo);
		
	}
}