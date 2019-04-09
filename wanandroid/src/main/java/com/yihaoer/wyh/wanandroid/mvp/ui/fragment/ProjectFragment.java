package com.yihaoer.wyh.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.app.base.SupportFragment;
import com.yihaoer.wyh.wanandroid.di.component.DaggerProjectComponent;
import com.yihaoer.wyh.wanandroid.di.module.ProjectModule;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectContract;
import com.yihaoer.wyh.wanandroid.mvp.presenter.ProjectPresenter;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.ProjectFragmentPageAdapter;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.ProjectTypeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends SupportFragment<ProjectPresenter> implements ProjectContract.View {

    private View mRootView;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<ProjectTypeItem> mProjectTypeList = new ArrayList<>();

    @BindView(R.id.project_tablayout)
    TabLayout mTabLayout;

    @BindView(R.id.project_viewpager)
    ViewPager mViewPager;

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerProjectComponent.builder()
                .appComponent(appComponent)
                .projectModule(new ProjectModule(this))
                .build()
                .Inject(this);

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null)
            mRootView = inflater.inflate(R.layout.fragment_project, container, false);
        return mRootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.loadProjectTypes(false);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showProjectTypeData(List<ProjectTypeItem> projectTypeBeanList) {
        this.mProjectTypeList = projectTypeBeanList;
        initViewPagerAndTabLayout();
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

    private void initViewPagerAndTabLayout() {
        mFragmentManager = getActivity().getSupportFragmentManager();
        mFragmentPagerAdapter = new ProjectFragmentPageAdapter(mFragmentManager, mFragmentList, mProjectTypeList);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
