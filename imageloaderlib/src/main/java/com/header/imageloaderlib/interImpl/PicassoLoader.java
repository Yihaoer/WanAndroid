package com.header.imageloaderlib.interImpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.header.imageloaderlib.bean.ImageScaleStyle;
import com.header.imageloaderlib.bean.LoaderOptions;
import com.header.imageloaderlib.inter.BitmapCallBack;
import com.header.imageloaderlib.inter.ILoaderStrategy;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;

public class PicassoLoader implements ILoaderStrategy {
    private volatile static Picasso mPicassoSingleton;
    private final String PICASSO_CACHE = "picasso-cache";
    private LruCache mLruCache;
    private Context mContext;

    public PicassoLoader(Context context) {
        this.mContext = context;
        mLruCache = new LruCache(context);
        mPicassoSingleton = new Picasso.Builder(context).memoryCache(mLruCache).build();
//        mPicassoSingleton = Picasso.get();
    }

    private Picasso getPicasso() {
        return mPicassoSingleton;
    }

    @Override
    public void clearMemoryCache() {
        mLruCache.clear();
    }

    @Override
    public void clearDiskCache() {
        File diskFile = new File(mContext.getCacheDir(), PICASSO_CACHE);
        if (diskFile.exists()) {
            //这边自行写删除代码
//	        FileUtil.deleteFile(diskFile);
        }
    }

    @Override
    public void loadImage(LoaderOptions options) {
        RequestCreator requestCreator = null;
        if (options.url != null) {
            requestCreator = getPicasso().load(options.url);
        } else if (options.file != null) {
            requestCreator = getPicasso().load(options.file);
        } else if (options.drawableResId != 0) {
            requestCreator = getPicasso().load(options.drawableResId);
        } else if (options.uri != null) {
            requestCreator = getPicasso().load(options.uri);
        }

        if (requestCreator == null) {
            throw new NullPointerException("requestCreator must not be null");
        }

        if (options.mTargetHeight > 0 && options.mTargetWidth > 0) {
            requestCreator.resize(options.mTargetWidth, options.mTargetHeight);
        }

        /**
         onlyScaleDown()：
         如果调用了resize(width,height)方法的话，Picasso一般会重新计算以改变图片的加载质量，比如一张小图变成一张大图进行展示的时候。但是如果我们的原图是比重新resize的新图规格大的时候，我们就可以调用onlyScaleDown()来直接进行展示而不再重新计算，缩短图片的加载计算时间。
         centerInside()：
         图片会被完整的展示，可能图片不会填充满ImageView，也有可能会被拉伸或者挤压，一般是等比例缩小。
         centerCrop()：
         图片会被裁剪，但是图片质量没有什么区别，等比例放大。
         fit()：
         Picasso会对图片的大小及ImageView进行测量，计算出最佳的大小及最佳的图片质量来进行图片展示，减少内存的消耗并对视图没有影响。
         */
        if (options.mTargetHeight > 0 && options.mTargetWidth > 0) {
            //必须在调用图片处理方法之前调用resize方法，否则Picasso会会报目标宽度/高度为0
            if (options.mImageScaleStyle == ImageScaleStyle.NONE) {
                //调用了resize(width,height)方法后，如果我们的原图是比重新resize的新图规格大的时候，
                //我们就可以调用onlyScaleDown()来直接进行展示而不再重新计算，缩短图片的加载计算时间
                requestCreator.onlyScaleDown();
            } else if (options.mImageScaleStyle == ImageScaleStyle.CENTERCROP) {
                requestCreator.centerCrop();
            } else if (options.mImageScaleStyle == ImageScaleStyle.SCALING) {
                requestCreator.centerInside();
            }
        }

        //此方法不可以和resize(width,height)方法同时使用
        if (options.mTargetHeight == 0 && options.mTargetWidth == 0) {
            if (options.mImageScaleStyle == ImageScaleStyle.FIT) {
                requestCreator.fit();
            }
        }

        if (options.config != null) {
            requestCreator.config(options.config);
        }

        if (options.mFailResId != 0) {
            requestCreator.error(options.mFailResId);
        } else if (options.mFailRes != null) {
            requestCreator.error(options.mFailRes);
        }

        if (options.mLoadingResId != 0) {
            requestCreator.placeholder(options.mLoadingResId);
        } else if (options.mLoadingRes != null) {
            requestCreator.placeholder(options.mLoadingRes);
        }

        if (!options.isCacheInMemory) {
            requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
        }
        if (!options.isCacheOndisk) {
            requestCreator.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
        }
        if (options.mBitmapAngle != 0) {
            requestCreator.transform(new PicassoCircleCornerTransform(options.mBitmapAngle));
        }
        if (options.mDegrees != 0) {
            requestCreator.rotate(options.mDegrees);
        }

        if (options.mTargetView instanceof ImageView) {
            requestCreator.into(((ImageView) options.mTargetView));
        } else if (options.callBack != null) {
            requestCreator.into(new PicassoTarget(options.callBack));
        }
    }


    class PicassoTarget implements Target {
        BitmapCallBack callBack;

        protected PicassoTarget(BitmapCallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if (this.callBack != null) {
                this.callBack.onBitmapLoaded(bitmap);
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            if (this.callBack != null) {
                this.callBack.onBitmapFailed(e);
            }
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }

    /**
     * 圆角图片转换工具类
     */
    class PicassoCircleCornerTransform implements Transformation {
        private float bitmapAngle;

        protected PicassoCircleCornerTransform(float corner) {
            this.bitmapAngle = corner;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            float roundPx = bitmapAngle;//圆角的横向半径和纵向半径
            Bitmap output = Bitmap.createBitmap(source.getWidth(),
                    source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
            final RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, rect, rect, paint);
            source.recycle();
            return output;
        }

        @Override
        public String key() {
            return getClass().getName() + Math.round(bitmapAngle);
        }
    }
}
