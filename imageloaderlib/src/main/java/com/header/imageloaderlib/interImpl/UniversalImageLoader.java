package com.header.imageloaderlib.interImpl;


import android.content.Context;
import android.widget.ImageView;

import com.header.imageloaderlib.bean.ImageScaleStyle;
import com.header.imageloaderlib.bean.LoaderOptions;
import com.header.imageloaderlib.inter.ILoaderStrategy;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by wyh on 2018/8/8.
 */

public class UniversalImageLoader implements ILoaderStrategy {

    private volatile static ImageLoader mImageLoaderSingleton;
    private ImageLoaderConfiguration mConfig;
    private Context mContext;

    public UniversalImageLoader(Context context){
        this.mContext = context;
        initImageLoader();
    }

    private static ImageLoader getImageLoader() {
        if (mImageLoaderSingleton == null) {
            synchronized (UniversalImageLoader.class) {
                if (mImageLoaderSingleton == null) {
                    mImageLoaderSingleton = ImageLoader.getInstance();
                }
            }
        }
        return mImageLoaderSingleton;
    }

    @Override
    public void loadImage(LoaderOptions options) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();

        if (options.mFailResId != 0) {
            builder.showImageForEmptyUri(options.mFailResId).showImageOnFail(options.mFailResId);
        } else if (options.mFailRes != null) {
            builder.showImageForEmptyUri(options.mFailRes).showImageOnFail(options.mFailRes);
        }

        if (options.mLoadingResId != 0) {
            builder.showImageOnLoading(options.mLoadingResId);
        } else if (options.mLoadingRes != null) {
            builder.showImageOnLoading(options.mLoadingRes);
        }

        if (!options.isCacheInMemory) {
            builder.cacheInMemory(false);
        } else if (options.isCacheInMemory) {
            builder.cacheInMemory(true);
        }

        if (!options.isCacheOndisk) {
            builder.cacheOnDisk(false);
        } else if (options.isCacheOndisk) {
            builder.cacheOnDisk(true);
        }

        /**
         * 缩放类型imageScaleType:
             EXACTLY :图像将完全按比例缩小的目标大小
             EXACTLY_STRETCHED:图片会缩放到目标大小完全,不足的会拉伸
             IN_SAMPLE_INT:图像将被二次采样的整数倍
             IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
             NONE:图片不会调整
         */
        if (options.mImageScaleStyle == ImageScaleStyle.NONE) {
            builder.imageScaleType(ImageScaleType.NONE);
        }else if(options.mImageScaleStyle == ImageScaleStyle.SCALING){
            builder.imageScaleType(ImageScaleType.EXACTLY_STRETCHED);
        }else if(options.mImageScaleStyle == ImageScaleStyle.FIT){
            builder.imageScaleType(ImageScaleType.EXACTLY);
        }

        if (options.config != null) {
            builder.bitmapConfig(options.config);
        }

        if (options.mBitmapAngle != 0) {
            //慎用，它会使用ARGB-8888模式创建了一个新的Bitmap对象
            builder.displayer(new RoundedBitmapDisplayer(options.mBitmapAngle));
        }

//        if (options.mDegrees != 0) {
//            //builder.considerExifParams(true);
//            //待实现
//                builder.displayer()
//        }

        if (options.mFadeDuration != 0) {
            builder.displayer(new FadeInBitmapDisplayer(options.mFadeDuration));
        }

        ImageSize imageSize = null;
        if (options.mTargetWidth > 0 && options.mTargetHeight > 0) {
            imageSize = new ImageSize(options.mTargetWidth, options.mTargetHeight);
        }

        if (options.mTargetView != null) {
            if (imageSize != null) {
                getImageLoader().displayImage(options.url, (ImageView) options.mTargetView, imageSize);
            } else {
                getImageLoader().displayImage(options.url, (ImageView) options.mTargetView, builder.build());
            }
        }
    }

    @Override
    public void clearMemoryCache() {
        mImageLoaderSingleton.clearMemoryCache();
    }

    @Override
    public void clearDiskCache() {
        mImageLoaderSingleton.clearDiskCache();
    }

    public void initImageLoader() {
        mConfig = new ImageLoaderConfiguration.Builder(mContext)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3) // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // 将保存的时候的URI名称用 MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(100) // 缓存的文件数量
//                .diskCache(new BaseDiskCache(Environment.getExternalStorageDirectory())) // 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(mContext, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build(); // 开始构建
        getImageLoader().init(mConfig);
    }
}
