package com.yihaoer.wyh.wanandroid.app.data.service;

import com.yihaoer.wyh.wanandroid.app.data.entity.project.ProjectArticleBean;
import com.yihaoer.wyh.wanandroid.app.data.entity.project.ProjectTypeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Author: Yihaoer
 * Description:
 */
public interface ProjectService {
    @GET("project/tree/json")
    Observable<ProjectTypeBean> getProjectTypes();

    @GET("project/list/{page}/json")
    Observable<ProjectArticleBean> getProjectArticleDataList(@Path("page") int page, @Query("cid") String cid);
}
