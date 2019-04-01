package com.yihaoer.wyh.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yihaoer.wyh.wanandroid.di.module.MainModule;
import com.yihaoer.wyh.wanandroid.mvp.ui.activity.MainActivity;

import dagger.Component;

/**
 * Author: Yihaoer
 * Description: MainComponent
 */
@ActivityScope
@Component(modules = MainModule.class , dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
