package com.yihaoer.wyh.wanandroid.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.yihaoer.wyh.wanandroid.mvp.contract.GuideContract;
import com.yihaoer.wyh.wanandroid.mvp.contract.HomeContract;
import com.yihaoer.wyh.wanandroid.mvp.model.GuideModel;
import com.yihaoer.wyh.wanandroid.mvp.model.HomeModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author: Yihaoer
 * Description: GuideModule
 */
@Module
public class GuideModule {

    private GuideContract.View view;

    public GuideModule(GuideContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    GuideContract.View provideGuideView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    GuideContract.Model provideGuideModel(GuideModel model){
        return model;
    }
}
