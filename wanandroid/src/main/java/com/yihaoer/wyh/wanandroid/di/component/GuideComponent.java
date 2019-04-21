package com.yihaoer.wyh.wanandroid.di.component;


import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yihaoer.wyh.wanandroid.di.module.GuideModule;
import com.yihaoer.wyh.wanandroid.di.module.HomeModule;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.main.GuideFragment;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.main.HomeFragment;

import dagger.Component;

/**
 * Author: Yihaoer
 * Description: GuideContract
 */
@ActivityScope
@Component(modules = GuideModule.class, dependencies = AppComponent.class)
public interface GuideComponent {
    void inject(GuideFragment GuideFragment);
}
