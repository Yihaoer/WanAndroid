package com.yihaoer.wyh.wanandroid.di.component;


import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yihaoer.wyh.wanandroid.di.module.HomeModule;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.main.HomeFragment;

import dagger.Component;

/**
 * Author: Yihaoer
 * Description: HomeComponent
 */
@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment homeFragment);
}
