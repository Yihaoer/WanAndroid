package com.yihaoer.wyh.wanandroid.mvp.presenter;

import com.jess.arms.mvp.BasePresenter;
import com.yihaoer.wyh.wanandroid.mvp.contract.MainContract;

import javax.inject.Inject;

/**
 * Author: Yihaoer
 * Description: 首页+侧滑栏的Presenter层
 */
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View view) {
        super(model, view);
    }

}
