package com.internal.cacheprovider.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.internal.cacheprovider.impl.CacheProviderServiceImpl;

/**
 * Unit test for simple App.
 */
public class CacheProviderServiceTest {
	CacheProviderServiceImpl cache;
	long sizeinitialcachesize;
	long evictedcacheSize;

	@Before
	public void init() {
		cache = new CacheProviderServiceImpl();
	}

	@Test
	public void testWhenObjectsAddedToCacheTest() {
		try {
			sizeinitialcachesize = cache.size();
			cache.add("1", "sapient", 1000, "time", 10);
			cache.add("2", "wellington", 2000, "time", 10);
			cache.add("3", "google", 3000, "time", 10);
			Thread.sleep(5 * 1000);
			evictedcacheSize = cache.size();
		} catch (InterruptedException e) {

		}
		Assert.assertEquals(null, sizeinitialcachesize, evictedcacheSize);
	}

	@Test
	public void testWhenObjectsGetFromCache() {
		try {
			cache.add("1", "sapient", 300, "time", 10);
			cache.add("2", "google", 100, "time", 10);
			cache.get("1");
			cache.get("2");
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {

		}
		Assert.assertEquals(cache.size(), 0L);
	}

	@Test
	public void testWhenCacheIsCleared() {
		try {
			cache.add("1", "sapient", 6000, "time", 10);
			cache.add("2", "google", 8000, "time", 10);
			cache.add("3", "wellington", 10000, "time", 10);
			cache.add("4", "amazon", 15000, "time", 10);
			cache.clear();
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {

		}
		Assert.assertEquals(cache.size(), 0L);
	}

}
