package com.yihaoer.wyh.wanandroid.mvp.presenter;

import com.jess.arms.mvp.BasePresenter;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectContract;

import javax.inject.Inject;

public class ProjectPresenter extends BasePresenter<ProjectContract.Model, ProjectContract.View> {

    @Inject
    public ProjectPresenter(ProjectContract.Model model, ProjectContract.View view) {
        super(model, view);
    }
}
