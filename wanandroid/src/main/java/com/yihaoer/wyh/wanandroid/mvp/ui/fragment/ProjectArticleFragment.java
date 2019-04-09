package com.yihaoer.wyh.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.app.base.SupportFragment;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectArticleContract;
import com.yihaoer.wyh.wanandroid.mvp.presenter.ProjectArticlePresenter;

/**
 * Author: Yihaoer
 * Description: 每个项目分类里的文章Fragment
 */
public class ProjectArticleFragment extends SupportFragment<ProjectArticlePresenter> implements ProjectArticleContract.View {
    private View mRootView;

    public static ProjectArticleFragment newInstance(Bundle bundle) {
        ProjectArticleFragment fragment = new ProjectArticleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null)
            mRootView = inflater.inflate(R.layout.fragment_project_article,container,false);
        return mRootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
}
