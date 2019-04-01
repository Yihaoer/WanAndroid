package com.yihaoer.wyh.wanandroid.mvp.presenter;

import com.jess.arms.mvp.BasePresenter;
import com.yihaoer.wyh.wanandroid.mvp.contract.GuideContract;

import javax.inject.Inject;

public class GuidePresenter extends BasePresenter<GuideContract.Model,GuideContract.View> {

    @Inject
    public GuidePresenter(GuideContract.Model model,GuideContract.View view){
        super(model,view);
    }
}
