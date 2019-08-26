package com.cha1024.bell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;

@SpringBootApplication
public class BellGitlabApplication {

	private static FIFOCache<Object, Object> cache = null;
	/**
	 * 获得缓存
	 * @return
	 */
	public static FIFOCache<Object, Object> getFifoCache(){
		if(cache == null) {
			cache = CacheUtil.newFIFOCache(300);
		}
		return cache;
	}
	public static void main(String[] args) {
		SpringApplication.run(BellGitlabApplication.class, args);
	}

}
