package com.yihaoer.wyh.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yihaoer.wyh.wanandroid.di.module.ProjectModule;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.ProjectFragment;

import dagger.Component;

/**
 * Author: Yihaoer
 * Description: ProjectComponent
 */
@ActivityScope
@Component(modules = ProjectModule.class,dependencies = AppComponent.class)
public interface ProjectComponent {
    void Inject(ProjectFragment projectFragment);
}
