package com.yihaoer.wyh.wanandroid.app.data.cache;

import com.yihaoer.wyh.wanandroid.app.data.entity.home.BannerBean;
import com.yihaoer.wyh.wanandroid.app.data.entity.home.HomeArticleBean;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Author: wuyihao
 * Description: 首页数据的Rxcache缓存
 */
public interface HomeCache {
    @LifeCache(duration = 10,timeUnit = TimeUnit.MINUTES)
    Observable<Reply<BannerBean>> getBannerDataList(Observable<BannerBean> bannerBeanObservable, EvictProvider evictProvider);

    @LifeCache(duration = 10,timeUnit = TimeUnit.MINUTES)
    Observable<Reply<HomeArticleBean>> getHomeArticleDataList(Observable<HomeArticleBean> homeArticleBeanObservable, DynamicKey dynamicKey,EvictProvider evictProvider);
}
