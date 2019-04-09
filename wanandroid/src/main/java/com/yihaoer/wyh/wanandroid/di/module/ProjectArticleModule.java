package com.yihaoer.wyh.wanandroid.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectArticleContract;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectContract;
import com.yihaoer.wyh.wanandroid.mvp.model.ProjectArticleModel;
import com.yihaoer.wyh.wanandroid.mvp.model.ProjectModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author: Yihaoer
 * Description: ProjectArticleModule
 */
@Module
public class ProjectArticleModule {

    private ProjectArticleContract.View view;

    public ProjectArticleModule(ProjectArticleContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    ProjectArticleContract.View provideProjectView(){
        return view;
    }

    @ActivityScope
    @Provides
    ProjectArticleContract.Model provideProjectModel(ProjectArticleModel model){
        return model;
    }
}
