package com.yihaoer.wyh.wanandroid.app.data.cache;

import com.yihaoer.wyh.wanandroid.app.data.entity.project.ProjectTypeBean;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Author: Yihaoer
 * Description: 项目分类的Rxcache缓存
 */
public interface ProjectCache {
    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<ProjectTypeBean>> getProjectTypes(Observable<ProjectTypeBean> projectTypeBeanObservable, EvictProvider evictProvider);
}
