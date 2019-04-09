package com.yihaoer.wyh.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.app.base.SupportFragment;
import com.yihaoer.wyh.wanandroid.di.component.DaggerProjectArticleComponent;
import com.yihaoer.wyh.wanandroid.di.module.ProjectArticleModule;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectArticleContract;
import com.yihaoer.wyh.wanandroid.mvp.presenter.ProjectArticlePresenter;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.ProjectRecycleViewAdapter;

import butterknife.BindView;

/**
 * Author: Yihaoer
 * Description: 每个项目分类里的文章Fragment
 */
public class ProjectArticleFragment extends SupportFragment<ProjectArticlePresenter> implements ProjectArticleContract.View {

    private View mRootView;
    private int pageId;
    private String cid;

    @BindView(R.id.project_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.project_refresh_layout)
    RefreshLayout mRefreshLayout;

    public static ProjectArticleFragment newInstance() {
        ProjectArticleFragment fragment = new ProjectArticleFragment();
        //        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerProjectArticleComponent.builder()
                .appComponent(appComponent)
                .projectArticleModule(new ProjectArticleModule(this))
                .build()
                .Inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null)
            mRootView = inflater.inflate(R.layout.fragment_project_article, container, false);
        return mRootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Log.i("asdsads","cid = " + cid);
        mPresenter.loadProjectArticleData(1, cid, true, false);
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

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public void setArticleRecyclerview(ProjectRecycleViewAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }
}
