package com.header.imageloaderlib.agent;


import android.net.Uri;
import android.util.Log;

import com.header.imageloaderlib.R;
import com.header.imageloaderlib.bean.LoaderOptions;
import com.header.imageloaderlib.inter.ILoaderStrategy;

import java.io.File;

/**
 * 图片加载类
 * 策略或者静态代理模式，开发者只需要关心PictureLoader + LoaderOptions
 */

public class PictureLoader {
    private static ILoaderStrategy sLoader;
    private static volatile PictureLoader sInstance;
    private int resIdForLoading = 0;
    private int resIdForFail = 0;

    private PictureLoader() {
    }

    //单例模式
    public static PictureLoader getInstance() {
        if (sInstance == null) {
            synchronized (PictureLoader.class) {
                if (sInstance == null) {
                    sInstance = new PictureLoader();
                }
            }
        }
        return sInstance;
    }

    /**
     * 提供全局替换图片加载框架的接口，若切换其它框架，可以实现一键全局替换
     */
    public PictureLoader setGlobalImageLoader(ILoaderStrategy loader) {
        sLoader = loader;
        return sInstance;
    }

    public LoaderOptions load(String url) {
        LoaderOptions options = new LoaderOptions(url);
        if (resIdForLoading != 0) {
            options.showImageOnLoading(resIdForLoading);
        }
        if(resIdForFail !=0){
            options.showImageOnFail(resIdForFail);
        }
        return options;
    }

    public LoaderOptions load(int drawable) {
        LoaderOptions options = new LoaderOptions(drawable);
        if (resIdForLoading != 0) {
            options.showImageOnLoading(resIdForLoading);
        }
        if(resIdForFail !=0){
            options.showImageOnFail(resIdForFail);
        }
        return options;
    }

    public LoaderOptions load(File file) {
        LoaderOptions options = new LoaderOptions(file);
        if (resIdForLoading != 0) {
            options.showImageOnLoading(resIdForLoading);
        }
        if(resIdForFail !=0){
            options.showImageOnFail(resIdForFail);
        }
        return options;
    }

    public LoaderOptions load(Uri uri) {
        LoaderOptions options = new LoaderOptions(uri);
        if (resIdForLoading != 0) {
            options.showImageOnLoading(resIdForLoading);
        }
        if(resIdForFail !=0){
            options.showImageOnFail(resIdForFail);
        }
        return options;
    }

    /**
     * 优先使用实时设置的图片loader，其次使用全局设置的图片loader
     *
     * @param options
     */
    public void loadOptions(LoaderOptions options) {
        if (options.loader != null) {
            options.loader.loadImage(options);
        } else {
            checkNotNull();
            sLoader.loadImage(options);
        }
    }

    public void clearMemoryCache() {
        checkNotNull();
        sLoader.clearMemoryCache();
    }

    public void clearDiskCache() {
        checkNotNull();
        sLoader.clearDiskCache();
    }

    private void checkNotNull() {
        if (sLoader == null) {
            throw new NullPointerException("you must be set your imageLoader at first!");
        }
    }

    /**
     * 提供全局设置"图片加载中"默认图的接口
     * @param resIdForLoading
     * @return
     */
    public PictureLoader setDefaultImageOnLoading(int resIdForLoading) {
        this.resIdForLoading = resIdForLoading;
        return sInstance;
    }

    /**
     * 提供全局设置"图片加载失败"默认图的接口
     * @param resIdForFail
     * @return
     */
    public PictureLoader setDefaultImageOnFail(int resIdForFail) {
        this.resIdForFail = resIdForFail;
        return sInstance;
    }
}