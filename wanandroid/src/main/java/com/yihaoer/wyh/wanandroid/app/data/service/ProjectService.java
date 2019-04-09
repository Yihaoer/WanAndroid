package com.yihaoer.wyh.wanandroid.app.data.service;

import com.yihaoer.wyh.wanandroid.app.data.entity.project.ProjectTypeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Author: Yihaoer
 * Description:
 */
public interface ProjectService {
    @GET("project/tree/json")
    Observable<ProjectTypeBean> getProjectTypes();
}
