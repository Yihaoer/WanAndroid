package com.yihaoer.wyh.wanandroid.app.config.options;


import android.content.Context;
import android.os.Environment;

import com.jess.arms.di.module.ClientModule;

import java.io.File;

import io.rx_cache2.internal.RxCache;

public class MyRxCacheConfiguration implements ClientModule.RxCacheConfiguration {
    @Override
    public RxCache configRxCache(Context context, RxCache.Builder builder) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"cache");
        // 当数据无法加载时，使用过期数据
        builder.useExpiredDataIfLoaderNotAvailable(true);
        return null;
    }
}
