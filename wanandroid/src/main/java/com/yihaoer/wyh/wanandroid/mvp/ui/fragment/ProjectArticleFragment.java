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
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.app.base.SupportFragment;
import com.yihaoer.wyh.wanandroid.di.component.DaggerProjectArticleComponent;
import com.yihaoer.wyh.wanandroid.di.module.ProjectArticleModule;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectArticleContract;
import com.yihaoer.wyh.wanandroid.mvp.presenter.ProjectArticlePresenter;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.ProjectRecycleViewAdapter;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.main.HomeFragment;

import butterknife.BindView;

/**
 * Author: Yihaoer
 * Description: 每个项目分类里的文章Fragment
 */
public class ProjectArticleFragment extends SupportFragment<ProjectArticlePresenter> implements ProjectArticleContract.View {

    private View mRootView;
    private String cid; //项目分类id
    private int mPageId = 1; //页码，从1开始
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

    public static ProjectArticleFragment newInstance() {
        return new ProjectArticleFragment();
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_project_article, container, false);
            isInit = true; //界面初始化完成标识
        }
        return mRootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        initFragmentation();
        lazyLoad();
        initRefreshLayout();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
//        mRefreshLayout.autoRefresh();
    }

    @Override
    public void hideLoading() {
//        mRefreshLayout.finishRefresh();
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

    /**
     * 初始化fragment的显示区域和初始页面
     */
    private void initFragmentation() {
        ProjectArticleFragment projectArticleFragment = findFragment(ProjectArticleFragment.class);
        if (projectArticleFragment == null) {
            loadRootFragment(R.id.content_fl, ProjectArticleFragment.newInstance(), true, false);
        }
    }

    /**
     * 设置项目分类的cid字段值
     *
     * @param cid
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * 获取项目分类的cid字段值
     *
     * @return
     */
    public String getCid() {
        return cid;
    }

    /**
     * 初始化下拉刷新和上拉加载
     */
    public void initRefreshLayout() {
        //设置 Header 为 官方主题 样式
        mRefreshLayout.setRefreshHeader(new MaterialHeader(mContext));
        //设置 Footer 为 正在加载 样式
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));

        //下拉刷新监听
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                //注意：项目分类api的页码是从1开始
                mPresenter.loadProjectArticleData(1, cid, true, true);
                mPageId = 1; //下拉刷新后重置页码
            }
        });

        //上拉加载监听
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPageId++;
                mPresenter.loadProjectArticleData(mPageId, cid, false, true);
            }
        });
    }

    @Override
    public void setArticleRecyclerview(ProjectRecycleViewAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void finishRefresh(int delayed) {
        mRefreshLayout.finishRefresh(delayed);
    }

    @Override
    public void finishLoadMore(int delayed) {
        mRefreshLayout.finishLoadMore(delayed);
    }

    //    @Override
    //    public void setArticleRecyclerview(List<ProjectArticleItem> list) {
    //        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    //        mRecyclerView.setLayoutManager(linearLayoutManager);
    //        ProjectRecycleViewAdapter adapter = new ProjectRecycleViewAdapter(getActivity(),list);
    //        mRecyclerView.setAdapter(adapter);
    //    }

    /**
     * 防止重复加载，首次加载成功后，除非下拉刷新才会去刷新数据
     */
    public void lazyLoad() {
        //判断是否未加载过，而且界面已经初始化完成
        if (!isLoadOver && isInit) {
            mPresenter.loadProjectArticleData(1, cid, true, true);//加载数据的方法
            //数据加载完毕,恢复标记,防止重复加载
            isLoadOver = true;
        }
    }
}
