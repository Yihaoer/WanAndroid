package com.yihaoer.wyh.wanandroid.mvp.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.wang.avi.AVLoadingIndicatorView;
import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.app.base.SupportFragment;
import com.yihaoer.wyh.wanandroid.di.component.DaggerHomeComponent;
import com.yihaoer.wyh.wanandroid.di.module.HomeModule;
import com.yihaoer.wyh.wanandroid.mvp.contract.HomeContract;
import com.yihaoer.wyh.wanandroid.mvp.presenter.HomePresenter;
import com.yihaoer.wyh.wanandroid.mvp.ui.activity.WebviewActivity;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.HomeRecyclerViewAdapter;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.BannerItem;
import com.yihaoer.wyh.wanandroid.mvp.ui.holder.BannerViewHolder;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.List;

import butterknife.BindView;

public class HomeFragment extends SupportFragment<HomePresenter> implements HomeContract.View {

    private View mRootView;
    private HomeRecyclerViewAdapter mAdapter;
    private MZBannerView mBanner;
    private View mHeaderView;
    private int mPageId = 0;

    @BindView(R.id.home_refresh_layout)
    RefreshLayout mRefreshLayout;

    @BindView(R.id.home_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading_view)
    AVLoadingIndicatorView mAvLoadingIndicatorView;

    private LinearLayoutManager mLinearLayoutManager;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null)
            mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        return mRootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRefreshLayout();
        mPresenter.loadBannerData(false);
        mPresenter.loadHomeArticleData(mPageId, true, false);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        mAvLoadingIndicatorView.show();
    }

    @Override
    public void hideLoading() {
        mAvLoadingIndicatorView.hide();
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

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null)
            mBanner.start();//开始轮播
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBanner != null)
            mBanner.pause();//暂停轮播
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
                mPresenter.loadBannerData(false);
                mPresenter.loadHomeArticleData(0, true, false);
                mPageId = 0; //下拉刷新后重置页码
//                mRefreshLayout.finishRefresh(2000);
                mBanner.start();
            }
        });

        //上拉加载监听
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPageId++;
                mPresenter.loadHomeArticleData(mPageId, false, false);
//                mRefreshLayout.finishLoadMore(2000);
            }
        });
    }

    @Override
    public void setArticleRecyclerview(HomeRecyclerViewAdapter adapter) {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        //设置banner的布局到ArticleRecyclerview的adapter中
        adapter.setmHeaderView(mBanner);
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
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

    @Override
    public void setBanner(List<BannerItem> list) {
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.banner_view_layout, null);
        mBanner = mHeaderView.findViewById(R.id.home_banner);
        mBanner.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getActivity().getWindowManager().getDefaultDisplay().getHeight() / 3));
        mBanner.setPages(list, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                mBanner.start();
                return new BannerViewHolder();
            }
        });
    }
}
