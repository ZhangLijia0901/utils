package com.utils.database.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.loader.LaunchedURLClassLoader;
import org.springframework.boot.loader.archive.Archive;
import org.springframework.boot.loader.archive.Archive.Entry;
import org.springframework.boot.loader.archive.ExplodedArchive;
import org.springframework.boot.loader.archive.JarFileArchive;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JarLoader {
//	extends ExecutableArchiveLauncher {

	static final String META_INF_DRIVER = "META-INF/driver/";
	static final Map<String, URL> JAR_URLS = new HashMap<>();
	private final static JarLoader jarLoader = new JarLoader();

	final Archive archive;

	private Archive createArchive() {
		ProtectionDomain protectionDomain = JarLoader.class.getProtectionDomain();
		CodeSource codeSource = protectionDomain.getCodeSource();
		URI location = null;
		try {
			location = (codeSource != null) ? codeSource.getLocation().toURI() : null;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String path = (location != null) ? location.getSchemeSpecificPart() : null;
		if (path == null) {
			throw new IllegalStateException("Unable to determine code source archive");
		}
		if (path.indexOf(".jar") != -1)
			path = path.substring(0, path.indexOf(".jar") + 4);
		else
			path = "/F:/github/utils-parent/app/target/classes";
		if (path.startsWith("file:"))
			path = path.substring(5);
//		path = path.endsWith(".jar!") ? path + "/" : path;
		log.info("jar url: [{}]", path);

		File root = new File(path);
		if (!root.exists()) {
			throw new IllegalStateException("Unable to determine code source archive from " + root);
		}
		try {
			return (root.isDirectory() ? new ExplodedArchive(root) : new JarFileArchive(root));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void loadJarUrl() throws IOException {
		List<Archive> archives = new ArrayList<>(this.archive.getNestedArchives(this::isNestedArchive));
		archives.forEach((archive) -> {
			try {
				URL url = archive.getUrl();
				String str_url = url.getFile();
				str_url = str_url.substring(0, str_url.lastIndexOf(".jar") + 4);
				String jarName = str_url.substring(str_url.lastIndexOf("/") + 1);

				JAR_URLS.put(jarName, url);
				log.debug("load jar, name:[{}] ; url: [{}]", jarName, archive.getUrl().toString());
			} catch (MalformedURLException e) {
				log.debug("获取jarURL失败", e);
			}
		});
	}

	public JarLoader() {
		this.archive = createArchive();
		try {
			loadJarUrl();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected boolean isNestedArchive(Entry entry) {
		return entry.getName().startsWith(META_INF_DRIVER) && entry.getName().endsWith(".jar");
	}

	/**
	 * 
	 * @param jarNames jar包名称
	 * @return
	 */
	public static ClassLoader createClassLoader(String... jarNames) {
		List<URL> urls = new ArrayList<>(jarNames.length);
		for (String jarName : jarNames)
			urls.add(JAR_URLS.get(jarName));

		try {
			return jarLoader.createClassLoader(urls.toArray(new URL[0]));
		} catch (Exception e) {
			log.error("创建类加载器失败", e);
			throw new RuntimeException(e);
		}
	}

	private ClassLoader createClassLoader(URL[] urls) throws Exception {
		return new LaunchedURLClassLoader(urls, getClass().getClassLoader());
	}
}
