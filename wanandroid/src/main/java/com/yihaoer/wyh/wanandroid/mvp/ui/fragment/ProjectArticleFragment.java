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
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.ProjectArticleItem;

import java.util.List;

import butterknife.BindView;

/**
 * Author: Yihaoer
 * Description: 每个项目分类里的文章Fragment
 */
public class ProjectArticleFragment extends SupportFragment<ProjectArticlePresenter> implements ProjectArticleContract.View {

    private View mRootView;
    private int pageId;
    private String cid;
    //是否加载过的标记
    private boolean isLoadOver = false;
    //Fragment对用户可见的标记
    private boolean isVisible = false;
    //是否初始化完成
    public boolean isInit = false;

    @BindView(R.id.project_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.project_refresh_layout)
    RefreshLayout mRefreshLayout;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerProjectArticleComponent.builder()
                .appComponent(appComponent)
                .projectArticleModule(new ProjectArticleModule(this))
                .build()
                .Inject(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("dsdsads","isVisible = " + getUserVisibleHint());
        if (getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = inflater.inflate(R.layout.fragment_project_article, container, false);
            isInit = true;
        }
        return mRootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        lazyLoad();
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

    @Override
    public void setArticleRecyclerview(List<ProjectArticleItem> list) {
        Log.i("sdsadsad",list.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        ProjectRecycleViewAdapter adapter = new ProjectRecycleViewAdapter(getActivity(),list);
        mRecyclerView.setAdapter(adapter);
    }

    public void lazyLoad() {
//        Log.i("dsdsads","isVisible = " + isVisible);

        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (!isLoadOver && isVisible && isInit) {
            mPresenter.loadProjectArticleData(1, cid, true, false);//加载数据的方法
            Log.i("dsdsads","cid = " + cid);
            //数据加载完毕,恢复标记,防止重复加载
            isLoadOver = true;
        }
    }
}
