package com.internal.cacheprovider.impl;

import java.lang.ref.SoftReference;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.internal.cacheprovider.api.CacheProviderService;
import com.internal.cacheprovider.config.CacheConfiguration;

public class CacheProviderServiceImpl implements CacheProviderService {

	private static final int CLEAN_UP_PERIOD_IN_SEC = 5;
	private long initiationTime;
	private long expiryTime;
	private String evictionStrategy;
	private long maxSize;

	private final ConcurrentHashMap<String, SoftReference<CacheObject>> cache = new ConcurrentHashMap<String, SoftReference<CacheObject>>();

	public CacheProviderServiceImpl() {
		Thread cleanerThread = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(CLEAN_UP_PERIOD_IN_SEC * 1000);
					CacheCleanUpService.cacheCleanUp(cache, evictionStrategy);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});
		cleanerThread.setDaemon(true);
		cleanerThread.start();
	}

	@Override
	public void add(String key, Object value, long expiryTimeCustom, String evictionStrategyCustom,
			long maxSizeCustom) {
		initiationTime = System.currentTimeMillis();
		expiryTime = (expiryTimeCustom == 0L) ? CacheConfiguration.getDefaultCacheEvictionTime() : expiryTimeCustom;
		evictionStrategy = (evictionStrategyCustom == null) ? CacheConfiguration.getDefaultCacheEvictionStrategy()
				: evictionStrategyCustom;
		maxSize = (maxSizeCustom == 0L) ? CacheConfiguration.getDefaultCacheEvictionSize() : maxSizeCustom;

		if (key == null) {
			return;
		}
		if (value == null) {
			cache.remove(key);
		} else {
			cache.put(key, new SoftReference<>(new CacheObject(value, initiationTime, expiryTime, maxSize)));
		}
	}

	@Override
	public void remove(String key) {
		cache.remove(key);
	}

	@Override
	public Object get(String key) {
		initiationTime = System.currentTimeMillis();
		return Optional.ofNullable(cache.get(key)).map(SoftReference::get)
				.filter(cacheObject -> !cacheObject.isExpired()).map(CacheObject::getValue).orElse(null);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public long size() {
		return cache.entrySet().stream().filter(entry -> Optional.ofNullable(entry.getValue()).map(SoftReference::get)
				.map(cacheObject -> !cacheObject.isExpired()).orElse(false)).count();
	}

	protected static class CacheObject {

		private Object value;
		private long expiryTime;
		private long initiationTime;
		private long maxSize;
		private CacheProviderServiceImpl cacheproviderImpl = new CacheProviderServiceImpl();

		CacheObject(Object value, long initiationTime, long expiryTime, long maxSize) {

			this.value = value;
			this.expiryTime = expiryTime;
			this.initiationTime = initiationTime;
			this.maxSize = maxSize;
		}

		boolean isExpired() {
			return (System.currentTimeMillis() - initiationTime) > expiryTime;
		}

		boolean isFull() {
			return cacheproviderImpl.size() >= maxSize;
		}

		Object getValue() {
			return value;
		}

	}

}
