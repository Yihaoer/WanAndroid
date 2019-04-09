package com.yihaoer.wyh.wanandroid.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectContract;
import com.yihaoer.wyh.wanandroid.mvp.model.ProjectModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author: Yihaoer
 * Description: ProjectModule
 */
@Module
public class ProjectModule {

    private ProjectContract.View view;

    public ProjectModule(ProjectContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    ProjectContract.View provideProjectView(){
        return view;
    }

    @ActivityScope
    @Provides
    ProjectContract.Model provideProjectModel(ProjectModel model){
        return model;
    }
}
