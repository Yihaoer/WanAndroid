package com.header.imageloaderlib.interImpl;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.module.GlideModule;
import com.header.imageloaderlib.R;
import com.header.imageloaderlib.bean.ImageScaleStyle;
import com.header.imageloaderlib.bean.LoaderOptions;
import com.header.imageloaderlib.inter.ILoaderStrategy;

/**
 * Created by wyh on 2018/8/9.
 */

public class GlideLoader implements ILoaderStrategy {

    private volatile static Glide mGlideSingleton;
    private Context mContext;

    public GlideLoader(Context context) {
        this.mContext = context;
        mGlideSingleton = Glide.get(mContext);
    }

    private Glide getGlide() {
        return mGlideSingleton;
    }

    @Override
    public void loadImage(LoaderOptions options) {
        DrawableTypeRequest drawableTypeRequest = null;

        if (options.url != null) {
            drawableTypeRequest = getGlide().with(mContext).load(options.url);
        } else if (options.file != null) {
            drawableTypeRequest = getGlide().with(mContext).load(options.file);
        } else if (options.drawableResId != 0) {
            drawableTypeRequest = getGlide().with(mContext).load(options.drawableResId);
        } else if (options.uri != null) {
            drawableTypeRequest = getGlide().with(mContext).load(options.uri);
        } else {
            drawableTypeRequest = getGlide().with(mContext).load("");
        }
//        if (drawableTypeRequest == null) {
//            throw new NullPointerException("drawableTypeRequest must not be null");
//        }

//        if(options.isDownLoadOnly){
//            drawableTypeRequest.downloadOnly(500,500);
//        }
//
//        //若资源不是gif动画将会失败，回调.error
//        if (options.asGif) {
//            drawableTypeRequest.asGif();
//        }
//
//        //无论资源是不是gif动画，都作为Bitmap对待。如果是gif动画会停在第一帧。
//        if (options.asBitmap) {
//            drawableTypeRequest.asBitmap();
//        }

        //请求给定系数的缩略图。如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示。系数sizeMultiplier必须在(0,1)之间
        if (options.mSizeMultiplier > 0) {
            drawableTypeRequest.thumbnail(options.mSizeMultiplier);
        }

        if (options.mTargetHeight > 0 && options.mTargetWidth > 0) {
            drawableTypeRequest.override(options.mTargetWidth, options.mTargetHeight);
        }

        if (options.mLoadingResId != 0) {
            drawableTypeRequest.placeholder(options.mLoadingResId);
        } else if (options.mLoadingRes != null) {
            drawableTypeRequest.placeholder(options.mLoadingRes);
        }

        if (options.mFailResId != 0) {
            drawableTypeRequest.error(options.mFailResId);
        } else if (options.mFailRes != null) {
            drawableTypeRequest.error(options.mFailRes);
        }

        if (!options.isCacheInMemory) {
            drawableTypeRequest.skipMemoryCache(true);
        } else if (options.isCacheInMemory) {
            drawableTypeRequest.skipMemoryCache(false);
        }

        /*
             DiskCacheStrategy.NONE： 表示不缓存任何内容。
             DiskCacheStrategy.SOURCE： 表示只缓存原始图片。
             DiskCacheStrategy.RESULT： 表示只缓存转换过后的图片（默认选项）。
             DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
        */
        if (!options.isCacheOndisk) {
            drawableTypeRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
        } else if (options.isCacheOndisk) {
            drawableTypeRequest.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        }

        if (options.mFadeDuration != 0) {
            //需要指定动画可使用
            //drawableTypeRequest.crossFade(animation,options.mFadeDuration);
            drawableTypeRequest.crossFade(options.mFadeDuration);
        }

        if (options.mBitmapAngle != 0) {
            drawableTypeRequest.transform(new GlideCircleCornerTransform(mContext, options.mBitmapAngle));
        }

        //表示让Glide在加载图片的过程中不进行图片变换
        if (options.isDonTramsform) {
            drawableTypeRequest.dontTransform();
        }

        //Glide新api已经默认实现一个渐入渐出300ms的动画效果，如不需要可设置dontAnimate()
        if (options.isDontAnimate) {
            drawableTypeRequest.dontAnimate();
        }

        if (options.mImageScaleStyle == ImageScaleStyle.NONE) {
            drawableTypeRequest.dontTransform();
        } else if (options.mImageScaleStyle == ImageScaleStyle.CENTERCROP) {
            drawableTypeRequest.centerCrop();
        } else if (options.mImageScaleStyle == ImageScaleStyle.FIT) {
            drawableTypeRequest.fitCenter();
        }

        //图片旋转，旋转角度为90的倍数
        if (options.mDegrees != 0) {
            drawableTypeRequest.transform(new GlideRotateTransformation(mContext, options.mDegrees));
        }

//        if(options.isPreload){
//            drawableTypeRequest.preload();
//        }

        if (options.mTargetView instanceof ImageView) {
            drawableTypeRequest.into((ImageView) options.mTargetView);
        }

    }

    @Override
    public void clearMemoryCache() {
        mGlideSingleton.clearMemory();
    }

    @Override
    public void clearDiskCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mGlideSingleton.clearDiskCache();
            }
        }).start();
    }

    /**
     * 圆角图片转换工具类
     */
    class GlideCircleCornerTransform extends BitmapTransformation {
        private float bitmapAngle;

        protected GlideCircleCornerTransform(Context context, float corner) {
            super(context);
            this.bitmapAngle = corner;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            float roundPx = bitmapAngle;//圆角的横向半径和纵向半径
            Bitmap output = Bitmap.createBitmap(toTransform.getWidth(),
                    toTransform.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, toTransform.getWidth(), toTransform.getHeight());
            final RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(toTransform, rect, rect, paint);
            toTransform.recycle();
            return output;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(bitmapAngle);
        }
    }

    /**
     * 图片旋转工具类，旋转角度为90的倍数
     */
    public class GlideRotateTransformation extends BitmapTransformation {

        //旋转默认0
        private float rotateRotationAngle = 0f;

        public GlideRotateTransformation(Context context, float rotateRotationAngle) {
            super(context);
            this.rotateRotationAngle = rotateRotationAngle;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            // 旋转图片动作
            Matrix matrix = new Matrix();
            matrix.postRotate(rotateRotationAngle);
            // 创建新的图片
            Bitmap resizedBitmap = Bitmap.createBitmap(toTransform, 0, 0,
                    toTransform.getWidth(), toTransform.getHeight(), matrix, true);

            return resizedBitmap;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(rotateRotationAngle);
        }
    }
}
