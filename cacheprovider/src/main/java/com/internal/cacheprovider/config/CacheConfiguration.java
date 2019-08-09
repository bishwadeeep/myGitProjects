package com.internal.cacheprovider.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CacheConfiguration {

	private static String defaultCacheEvictionStrategy = null;
	private static long defaultCacheEvictionTime = 0L;
	private static long deafultCacheEvictionSize = 0L;

	static {

		try (InputStream input = CacheConfiguration.class.getClassLoader()
				.getResourceAsStream("cacheconfig.properties")) {

			Properties prop = new Properties();

			// load cache properties file
			prop.load(input);

			// get the property value and print it out
			defaultCacheEvictionStrategy = prop.getProperty("cacheEvictionStrategy", "time");
			defaultCacheEvictionTime = Long.parseLong(prop.getProperty("cacheEvictionTime", "3000"));
			deafultCacheEvictionSize = Long.parseLong(prop.getProperty("cacheEvictionSize", "10"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static String getDefaultCacheEvictionStrategy() {

		return defaultCacheEvictionStrategy;
	}

	public static long getDefaultCacheEvictionTime() {

		return defaultCacheEvictionTime;
	}

	public static long getDefaultCacheEvictionSize() {

		return deafultCacheEvictionSize;
	}

}