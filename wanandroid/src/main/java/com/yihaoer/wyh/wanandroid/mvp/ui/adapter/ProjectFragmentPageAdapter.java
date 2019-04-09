package com.yihaoer.wyh.wanandroid.mvp.ui.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.yihaoer.wyh.wanandroid.mvp.ui.entity.ProjectTypeItem;
import com.yihaoer.wyh.wanandroid.mvp.ui.fragment.ProjectArticleFragment;

import java.util.List;

/**
 * Author: Yihaoer
 * Description: 项目分类fragment的适配器
 */
public class ProjectFragmentPageAdapter extends FragmentPagerAdapter {

    private List<ProjectTypeItem> projectTypeList;
    private List<ProjectArticleFragment> fragmentList;

    public ProjectFragmentPageAdapter(FragmentManager fm, List<ProjectArticleFragment> fragmentList, List<ProjectTypeItem> projectTypeList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.projectTypeList = projectTypeList;
    }

    @Override
    public Fragment getItem(int i) {
//        ProjectArticleFragment fragment = fragmentList.get(i);
//        if (fragment != null){
//            return fragment;
//        }else {
//            fragment = ProjectArticleFragment.newInstance();
//            fragment.setCid(projectTypeList.get(i).getId());
//            fragmentList.add(fragment);
//        }
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return projectTypeList.get(position).getName();
    }

    public void setData(List<ProjectArticleFragment> fragmentList, List<ProjectTypeItem> projectTypeList){
        this.fragmentList = fragmentList;
        this.projectTypeList = projectTypeList;
//        this.fragmentList.addAll(fragmentList);
//        this.projectTypeList.addAll(projectTypeList);
        notifyDataSetChanged();
    }
}
