package demonmodders.crymod.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.crash.CallableMinecraftVersion;
import net.minecraft.util.StringTranslate;
import net.minecraftforge.common.Configuration;

import com.google.common.io.ByteStreams;

public class UpdateChecker implements Runnable {

	public static final String VERSION = "0.1";
	
	private static final String UPDATE_URL = "http://www.take-weiland.de/summoningmod/versions.php";

	public static final String MINECRAFT_VERSION = new CallableMinecraftVersion(null).minecraftVersion();
	
	private UpdateStatus currentStatus;
	
	private Thread currentThread = null;
	
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
				if (splitted.length != 4 || !splitted[0].equals(MINECRAFT_VERSION)) { // don't care if the line is invalid or its not for this version
					continue;
				}
				isUpToDate = splitted[1].equals(VERSION);
				break;
			}
			Crymod.logger.info(isUpToDate ? "Installation is up-to-date." : "Updates are available");
			
			reader.close();
			in.close();
			
			synchronized (this) {
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
	
	public void startDownload() {
		synchronized (this) {
			if (currentDownloadedInfo != null && currentThread == null) {
				try {
					URL url = new URL(currentDownloadedInfo[3]);
					currentThread = new Thread(new Downloader(url));
					currentThread.start();
				} catch (MalformedURLException e) {
					postStatus(UpdateStatus.FAILED);
				}
			}
		}
	}
	
	private List<String> getUpdateInfoForExternal() {
		synchronized (this) {
			return currentDownloadedInfo == null ? null : Collections.unmodifiableList(new ArrayList(Arrays.asList(currentDownloadedInfo)));
		}
	}
	
	public static enum UpdateStatus {
		LOADING("loading", false, false),
		CHECKING("checking", false, false),
		DISABLED("disabled", false, false),
		FAILED("failed", true, false),
		UP_TO_DATE("uptodate", true, false),
		UPDATES_AVAILABLE("available", true, true),
		DOWNLOADING("downloading", false, false),
		DOWNLOAD_FAILED("downloadFailed", true, false),
		INSTALL_FAILED("installFailed", true, false),
		CANT_INSTALL("cantInstall", true, false),
		COPYING("copying", false, false),
		COMPLETE("complete", false, false);
		
		private final String langKey;
		private final boolean canInstall;
		private final boolean canRetry;
		
		private UpdateStatus(String langKey, boolean canRetry, boolean canInstall) {
			this.langKey = "crymod.ui.updates." + langKey;
			this.canInstall = canInstall;
			this.canRetry = canRetry;
		}
		
		public String translate() {
			return StringTranslate.getInstance().translateKey(langKey);
		}

		public boolean canInstall() {
			return canInstall;
		}

		public boolean canRetry() {
			return canRetry;
		}
	}
	
	public static interface UpdateStatusHandler {
		
		public void handleStatus(UpdateStatus newStatus, List<String> updateInfo);
		
	}
	
	private class Downloader implements Runnable {
		
		private final URL source;
		
		private Downloader(URL source) {
			this.source = source;
		}
		
		@Override
		public void run() {
			postStatus(UpdateStatus.DOWNLOADING);
			File target;
			try {
				target = File.createTempFile("summoningmod", ".jar");
				target.deleteOnExit();
				InputStream in = source.openStream();
				OutputStream out = new FileOutputStream(target);
				
				ByteStreams.copy(in, out);
				
				out.close();
				in.close();
				
			} catch (IOException e) {
				postStatus(UpdateStatus.DOWNLOAD_FAILED);
				return;
			}
			
			postStatus(UpdateStatus.COPYING);
			
			try {
				// this should give us our mod-jar file
				URL codeSource = getClass().getProtectionDomain().getCodeSource().getLocation();
				if (codeSource.getProtocol().equals("jar")) {
					JarURLConnection connection = ((JarURLConnection)codeSource.openConnection());
					File sourceFile = new File(connection.getJarFileURL().toURI());
					
					InputStream in = new FileInputStream(target);
					OutputStream out = new FileOutputStream(sourceFile);
					
					ByteStreams.copy(in, out);
					
					out.close();
					in.close();
					postStatus(UpdateStatus.COMPLETE);
				} else {
					postStatus(UpdateStatus.CANT_INSTALL);
				}
			} catch (IOException e) {
				postStatus(UpdateStatus.INSTALL_FAILED);
			} catch (URISyntaxException e) {
				postStatus(UpdateStatus.INSTALL_FAILED);
			}
			
			synchronized (UpdateChecker.this) {
				currentThread = null;
			}
		}
	}
}