package com.yihaoer.wyh.wanandroid.mvp.presenter;

import com.jess.arms.mvp.BasePresenter;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectArticleContract;
import javax.inject.Inject;

/**
 * Author: Yihaoer
 * Description:
 */
public class ProjectArticlePresenter extends BasePresenter<ProjectArticleContract.Model,ProjectArticleContract.View> {

    @Inject
    public ProjectArticlePresenter(ProjectArticleContract.Model model,ProjectArticleContract.View view){
        super(model,view);
    }
}
