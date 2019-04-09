package com.yihaoer.wyh.wanandroid.app.data.service;


import com.yihaoer.wyh.wanandroid.app.data.entity.home.BannerBean;
import com.yihaoer.wyh.wanandroid.app.data.entity.home.HomeArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Author: Yihaoer
 * Description:
 */
public interface HomeService {
    @GET("banner/json")
    Observable<BannerBean> getBannerDataList();

    @GET("article/list/{page}/json")
    Observable<HomeArticleBean> getHomeArticleDataList(@Path("page") int pageId);
}
