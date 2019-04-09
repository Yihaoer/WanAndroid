package com.yihaoer.wyh.wanandroid.mvp.presenter;

import android.app.Application;
import android.content.Context;

import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yihaoer.wyh.wanandroid.app.data.entity.project.ProjectArticleBean;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectArticleContract;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectContract;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.ProjectRecycleViewAdapter;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.ProjectArticleItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Author: Yihaoer
 * Description:
 */
public class ProjectArticlePresenter extends BasePresenter<ProjectArticleContract.Model, ProjectArticleContract.View> {

    private ProjectRecycleViewAdapter mProjectRecycleViewAdapter;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public ProjectArticlePresenter(ProjectArticleContract.Model model, ProjectArticleContract.View view, AppManager appManager, Application application) {
        super(model, view);
        this.mAppManager = appManager;
        this.mApplication = application;

    }

    public void loadProjectArticleData(int pageId, String cid, boolean refresh, boolean clearCache) {
        mModel.getProjectArticleDataList(pageId, cid, clearCache)
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<ProjectArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ProjectArticleBean projectArticleBean) {
                        setArticleRecyclerViewAdapter(mModel.parseProjectArticleData(projectArticleBean.getData().getDatas()),refresh);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 设置首页文章的数据适配器
     *
     * @param list    数据集合
     * @param refresh 是否是下拉刷新？   true：下拉刷新    false：上拉加载
     */
    public void setArticleRecyclerViewAdapter(List<ProjectArticleItem> list, boolean refresh) {
        if (mProjectRecycleViewAdapter == null) {
            mProjectRecycleViewAdapter = new ProjectRecycleViewAdapter(getContext(), list);
            mRootView.setArticleRecyclerview(mProjectRecycleViewAdapter);
        } else {
            if (refresh) {
                mProjectRecycleViewAdapter.setData(list);
            } else {
                mProjectRecycleViewAdapter.addData(list);
            }
        }
    }

    private Context getContext() {
        if (mAppManager.getTopActivity() == null) {
            return mAppManager.getTopActivity();
        } else {
            return mApplication;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAppManager = null;
        mApplication = null;
    }
}
