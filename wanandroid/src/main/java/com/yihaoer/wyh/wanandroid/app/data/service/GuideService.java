package com.yihaoer.wyh.wanandroid.app.data.service;

import com.yihaoer.wyh.wanandroid.app.data.entity.guide.GuideBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Author: Yihaoer
 * Description:
 */
public interface GuideService {
    @GET("https://www.wanandroid.com/navi/json")
    Observable<GuideBean> getGuideDataList();
}
