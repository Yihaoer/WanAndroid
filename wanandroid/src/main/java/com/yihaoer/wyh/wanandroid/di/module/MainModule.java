package com.yihaoer.wyh.wanandroid.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.yihaoer.wyh.wanandroid.mvp.contract.MainContract;
import com.yihaoer.wyh.wanandroid.mvp.model.MainModel;

import dagger.Module;
import dagger.Provides;

/**
 * Author: Yihaoer
 * Description: MainModule
 */
@Module
public class MainModule {

    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideMainModel(MainModel model) {
        return model;
    }
}
