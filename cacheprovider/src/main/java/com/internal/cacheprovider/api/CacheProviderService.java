package com.internal.cacheprovider.api;

public interface CacheProviderService {
	
	void add(String key, Object value, long expiryTime, String evictionStrategy, long maxSize);
	 
    void remove(String key);
 
    Object get(String key);
 
    void clear();
 
    long size();

}
