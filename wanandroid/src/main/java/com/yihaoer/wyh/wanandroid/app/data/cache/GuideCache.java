package com.yihaoer.wyh.wanandroid.app.data.cache;

import com.yihaoer.wyh.wanandroid.app.data.entity.guide.GuideBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Author: Yihaoer
 * Description: 导航数据的Rxcache缓存
 */
public interface GuideCache {
    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<GuideBean>> getGuideDataList(Observable<GuideBean> guideBeanObservable, EvictProvider evictProvider);
}
