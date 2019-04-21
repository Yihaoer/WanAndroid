package com.yihaoer.wyh.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.wang.avi.AVLoadingIndicatorView;
import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.app.base.SupportFragment;
import com.yihaoer.wyh.wanandroid.di.component.DaggerProjectComponent;
import com.yihaoer.wyh.wanandroid.di.module.ProjectModule;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectContract;
import com.yihaoer.wyh.wanandroid.mvp.presenter.ProjectPresenter;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.ProjectFragmentPageAdapter;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.ProjectTypeItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

public class ProjectFragment extends SupportFragment<ProjectPresenter> implements ProjectContract.View {

    private View mRootView;
    private ProjectFragmentPageAdapter mFragmentPagerAdapter;
    private FragmentManager mFragmentManager;
    private List<ProjectArticleFragment> mFragmentList = new ArrayList<>();
    private List<ProjectTypeItem> mProjectTypeList = new ArrayList<>();
    private ProjectArticleFragment mProjectArticleFragment;

    @BindView(R.id.project_tablayout)
    TabLayout mTabLayout;

    @BindView(R.id.project_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.loading_view)
    AVLoadingIndicatorView mAvLoadingIndicatorView;

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
        addProjectArticleFragment();
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

    /**
     * 初始化ViewPager和TabLayout
     */
    private void initViewPagerAndTabLayout() {
        mFragmentManager = getActivity().getSupportFragmentManager();
        mFragmentPagerAdapter = new ProjectFragmentPageAdapter(mFragmentManager, mFragmentList, mProjectTypeList);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mProjectTypeList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mFragmentPagerAdapter.getPageTitle(i)));
        }
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

    /**
     * 根据项目分类数量来添加fragment
     */
    private void addProjectArticleFragment() {
        for (int i = 0; i < mProjectTypeList.size(); i++) {
            mProjectArticleFragment = new ProjectArticleFragment();
            mProjectArticleFragment.setCid(mProjectTypeList.get(i).getId());
            mFragmentList.add(mProjectArticleFragment);
        }
        mFragmentPagerAdapter.setData(mFragmentList, mProjectTypeList);
    }
}
