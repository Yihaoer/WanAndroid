package com.yihaoer.wyh.wanandroid.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.yihaoer.wyh.wanandroid.di.module.ProjectArticleModule;
import com.yihaoer.wyh.wanandroid.di.module.ProjectModule;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.ProjectArticleFragment;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.ProjectFragment;

import dagger.Component;

/**
 * Author: Yihaoer
 * Description: ProjectArticleComponent
 */
@ActivityScope
@Component(modules = ProjectArticleModule.class,dependencies = AppComponent.class)
public interface ProjectArticleComponent {
    void Inject(ProjectArticleFragment projectArticleFragment);
}
