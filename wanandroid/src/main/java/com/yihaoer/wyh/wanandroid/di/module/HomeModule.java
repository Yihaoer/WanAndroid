package com.yihaoer.wyh.wanandroid.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.yihaoer.wyh.wanandroid.mvp.contract.HomeContract;
import com.yihaoer.wyh.wanandroid.mvp.model.HomeModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author: Yihaoer
 * Description: HomeModule
 */
@Module
public class HomeModule {

    private HomeContract.View view;

    public HomeModule(HomeContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    HomeContract.View provideHomeView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    HomeContract.Model provideHomeModel(HomeModel model){
        return model;
    }
}
