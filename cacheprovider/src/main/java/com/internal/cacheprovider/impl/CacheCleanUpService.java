package com.internal.cacheprovider.impl;

import java.lang.ref.SoftReference;
import java.util.Observable;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.internal.cacheprovider.impl.CacheProviderServiceImpl.CacheObject;

public class CacheCleanUpService extends Observable {

	private static CacheCleanUpService cacheCleanSevice = new CacheCleanUpService();

	protected static void cacheCleanUp(ConcurrentHashMap<String, SoftReference<CacheObject>> cache,
			String evictionStrategy) {
		ConcurrentHashMap<String, SoftReference<CacheObject>> initialcache = cache;

		if ((evictionStrategy != null) && evictionStrategy.equalsIgnoreCase("time")) {

			cache.entrySet().removeIf(entry -> Optional.ofNullable(entry.getValue()).map(SoftReference::get)
					.map(CacheObject::isExpired).orElse(false));
			cacheCleanSevice.checkChange(initialcache, cache);
		}

		else if (evictionStrategy == null) {

			cache.entrySet().removeIf(entry -> Optional.ofNullable(entry.getValue()).map(SoftReference::get)
					.map(CacheObject::isExpired).orElse(false));
			cacheCleanSevice.checkChange(initialcache, cache);

		}

		else {

			cache.entrySet().removeIf(entry -> Optional.ofNullable(entry.getValue()).map(SoftReference::get)
					.map(CacheObject::isFull).orElse(false));
			cacheCleanSevice.checkChange(initialcache, cache);
		}
	}

	private void checkChange(ConcurrentHashMap<String, SoftReference<CacheObject>> initialcache,
			ConcurrentHashMap<String, SoftReference<CacheObject>> evictedcache) {
		if (!initialcache.equals(evictedcache)) {
			setChanged();
		}
	}
}
