package com.yihaoer.wyh.wanandroid.mvp.presenter;

import com.jess.arms.mvp.BasePresenter;
import com.yihaoer.wyh.wanandroid.mvp.contract.HierarchyContract;

import javax.inject.Inject;

public class HierarchyPresenter extends BasePresenter<HierarchyContract.Model, HierarchyContract.View> {

    @Inject
    public HierarchyPresenter(HierarchyContract.Model model, HierarchyContract.View view) {
        super(model, view);
    }
}
