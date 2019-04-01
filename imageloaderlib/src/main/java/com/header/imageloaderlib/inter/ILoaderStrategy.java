package com.header.imageloaderlib.inter;


import com.header.imageloaderlib.bean.LoaderOptions;

public interface ILoaderStrategy {

	void loadImage(LoaderOptions options);

	/**
	 * 清理内存缓存
	 */
	void clearMemoryCache();

	/**
	 * 清理磁盘缓存
	 */
	void clearDiskCache();

}
