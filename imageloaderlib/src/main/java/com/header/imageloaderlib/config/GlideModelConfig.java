package com.header.imageloaderlib.config;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by pc on 2018/8/27.
 */

public class GlideModelConfig implements GlideModule {

    int diskSize = 1024 * 1024 * 50;  //磁盘缓存大小
    int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存
    String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WanAndroid/cache/image";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 定义内部缓存和外部缓存的大小和位置
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize));  //内存中
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context,"iamge", diskSize)); //sd卡中
        builder.setDiskCache(new DiskLruCacheFactory(dir, diskSize));

        // 自定义内存和图片池大小
        builder.setMemoryCache(new LruResourceCache(memorySize));
        builder.setBitmapPool(new LruBitmapPool(memorySize));

        // 定义图片格式
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);  //默认
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
