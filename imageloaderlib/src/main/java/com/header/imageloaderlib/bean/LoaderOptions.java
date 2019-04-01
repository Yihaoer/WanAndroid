package com.header.imageloaderlib.bean;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.header.imageloaderlib.agent.PictureLoader;
import com.header.imageloaderlib.inter.BitmapCallBack;
import com.header.imageloaderlib.inter.ILoaderStrategy;

import java.io.File;

/**
 * 该类为图片加载框架的通用属性封装，不能耦合任何一方的框架
 */
public class LoaderOptions {
    public int mLoadingResId;
    public int mFailResId;
    public Drawable mLoadingRes;
    public Drawable mFailRes;
    public boolean isCenterCrop;
    public boolean isCenterInside;
    public boolean isFitCenter;
    public boolean isCacheInMemory = true; //是否缓存到内存
    public boolean isCacheOndisk = true;  //是否缓存到磁盘
    public boolean isImageScaleType; //是否设置图片缩放方式
    //    public boolean isPreload; //是否预加载
    public ImageScaleStyle mImageScaleStyle; //图片缩放方式枚举
    public Bitmap.Config config = Bitmap.Config.RGB_565;
    public int mTargetWidth;
    public int mTargetHeight;
    public int mBitmapAngle; //圆角角度
    public int mFadeDuration; //图片加载渐入过度时间
    public int mDegrees; //旋转角度.注意:picasso针对三星等本地图片，默认旋转回0度，即正常位置。此时不需要自己rotate
    public View mTargetView;//targetView展示图片
    public BitmapCallBack callBack;
    public String url;
    public File file;
    public int drawableResId;
    public Uri uri;
    public ILoaderStrategy loader;//实时切换图片加载库
    public int mPreloadWidth, getmPreloadHeight; //预加载宽高
    public boolean isDonTramsform; //是否禁止转换
    public boolean isDontAnimate; //是否禁止渐入过渡动画
    public boolean asGif; //是否做为gif图展示
    public boolean asBitmap; //是否作为普通bitmap展示
    public boolean isDownLoadOnly; //是否在后台线程同步下载图片
    public float mSizeMultiplier; //缩略图系数


    public LoaderOptions(String url) {
        this.url = url;
    }

    public LoaderOptions(File file) {
        this.file = file;
    }

    public LoaderOptions(int drawableResId) {
        this.drawableResId = drawableResId;
    }

    public LoaderOptions(Uri uri) {
        this.uri = uri;
    }

    public void into(View targetView) {
        this.mTargetView = targetView;
        PictureLoader.getInstance().loadOptions(this);
    }

    public void bitmap(BitmapCallBack callBack) {
        this.callBack = callBack;
        PictureLoader.getInstance().loadOptions(this);
    }

    public LoaderOptions loader(ILoaderStrategy imageLoader) {
        this.loader = imageLoader;
        return this;
    }

    public LoaderOptions showImageOnLoading(@DrawableRes int loadingResId) {
        this.mLoadingResId = loadingResId;
        return this;
    }

    public LoaderOptions showImageOnLoading(Drawable loadingRes) {
        this.mLoadingRes = loadingRes;
        return this;
    }

    public LoaderOptions showImageOnFail(@DrawableRes int failResId) {
        this.mFailResId = failResId;
        return this;
    }

    public LoaderOptions showImageOnFail(Drawable failRes) {
        this.mFailRes = failRes;
        return this;
    }

//    public LoaderOptions centerCrop() {
//        isCenterCrop = true;
//        return this;
//    }
//
//    public LoaderOptions centerInside() {
//        isCenterInside = true;
//        return this;
//    }
//
//    public LoaderOptions fitCenter() {
//        isFitCenter = true;
//        return this;
//    }

    public LoaderOptions imageScaleType(ImageScaleStyle imageScaleStyle) {
        isImageScaleType = true;
        this.mImageScaleStyle = imageScaleStyle;
        return this;
    }

    public LoaderOptions config(Bitmap.Config config) {
        this.config = config;
        return this;
    }

    public LoaderOptions resize(int targetWidth, int targetHeight) {
        this.mTargetWidth = targetWidth;
        this.mTargetHeight = targetHeight;
        return this;
    }

    /**
     * 圆角
     *
     * @param bitmapAngle 度数
     * @return
     */
    public LoaderOptions angle(int bitmapAngle) {
        this.mBitmapAngle = bitmapAngle;
        return this;
    }

    /**
     * 旋转
     *
     * @param degrees 度数
     * @return
     */
    public LoaderOptions rotate(int degrees) {
        this.mDegrees = degrees;
        return this;
    }

    public LoaderOptions fade(int duration) {
        this.mFadeDuration = duration;
        return this;
    }

    public LoaderOptions cacheInMemory(boolean isCacheInMemory) {
        this.isCacheInMemory = isCacheInMemory;
        return this;
    }

    public LoaderOptions cacheOnDisk(boolean isCacheOndisk) {
        this.isCacheOndisk = isCacheOndisk;
        return this;
    }

//    public LoaderOptions preload(int preloadWith, int preloadHeight) {
//        this.mPreloadWidth = preloadHeight;
//        this.getmPreloadHeight = preloadHeight;
//        return this;
//    }

    public LoaderOptions dontTransform() {
        isDonTramsform = true;
        return this;
    }

    public LoaderOptions dontAnimate() {
        isDontAnimate = true;
        return this;
    }

    public LoaderOptions asGif() {
        asGif = true;
        return this;
    }

    public LoaderOptions asBitmap() {
        asBitmap = true;
        return this;
    }

    public LoaderOptions thumbnail(float sizeMultiplier) {
        this.mSizeMultiplier = sizeMultiplier;
        return this;
    }

    public LoaderOptions downloadOnly(){
        isDownLoadOnly = true;
        return this;
    }
}


