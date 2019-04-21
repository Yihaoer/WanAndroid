package com.yihaoer.wyh.wanandroid.mvp.ui.fragment.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.wang.avi.AVLoadingIndicatorView;
import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.app.base.SupportFragment;
import com.yihaoer.wyh.wanandroid.di.component.DaggerGuideComponent;
import com.yihaoer.wyh.wanandroid.di.module.GuideModule;
import com.yihaoer.wyh.wanandroid.mvp.contract.GuideContract;
import com.yihaoer.wyh.wanandroid.mvp.presenter.GuidePresenter;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.GuideRecycleViewAdapter;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.GuideItem;

import java.util.List;

import butterknife.BindView;
import q.rorbin.badgeview.Badge;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class GuideFragment extends SupportFragment<GuidePresenter> implements GuideContract.View {

    private View mRootView;

    @BindView(R.id.guide_vertical_tablayout)
    VerticalTabLayout mVerticalTabLayout;

    @BindView(R.id.guide_article_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading_view)
    AVLoadingIndicatorView mAvLoadingIndicatorView;

    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGuideComponent.builder()
                .appComponent(appComponent)
                .guideModule(new GuideModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null)
            mRootView = inflater.inflate(R.layout.fragment_guide, container, false);
        return mRootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.loadGuideData(false);
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
    public void initVerticalTablayout(List<List<GuideItem>> lists) {
        mVerticalTabLayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return lists.size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return new ITabView.TabBadge.Builder().setBackgroundColor(Color.WHITE)
                        .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                            @Override
                            public void onDragStateChanged(int dragState, Badge badge, View targetView) {

                            }
                        }).build();
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new TabView.TabTitle.Builder()
                        .setContent(lists.get(position).get(0).getChapterName())
                        .setTextColor(R.color.white, Color.BLACK)
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return -1;
            }
        });

        mVerticalTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                mRecyclerView.smoothScrollToPosition(position); //点击某个tab标签，定位到对应的RecyclerView的item
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setArticleRecyclerview(GuideRecycleViewAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                int position = linearLayoutManager.findFirstVisibleItemPosition();
//                mVerticalTabLayout.setTabSelected(position);
//            }
//        });
    }
}
