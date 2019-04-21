package com.yihaoer.wyh.wanandroid.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Adapter;

import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yihaoer.wyh.wanandroid.app.data.entity.guide.GuideBean;
import com.yihaoer.wyh.wanandroid.mvp.contract.GuideContract;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.GuideRecycleViewAdapter;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.GuideItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

public class GuidePresenter extends BasePresenter<GuideContract.Model, GuideContract.View> {

    private GuideRecycleViewAdapter mAdapter;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public GuidePresenter(GuideContract.Model model, GuideContract.View view,
                          AppManager appManager, Application application) {
        super(model, view);
        this.mAppManager = appManager;
        this.mApplication = application;
    }

    public void loadGuideData(boolean clearCache) {
        mModel.getGuideDataList(clearCache)
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<GuideBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GuideBean guideBean) {
                        mRootView.initVerticalTablayout(mModel.parseGuideData(guideBean.getData()));
                        setArticleRecyclerViewAdapter(mModel.parseGuideData(guideBean.getData()));
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
     * 设置导航页文章标签的适配器
     *
     * @param lists
     */
    public void setArticleRecyclerViewAdapter(List<List<GuideItem>> lists) {
        if (mAdapter == null) {
            mAdapter = new GuideRecycleViewAdapter(getContext(), lists);
            mRootView.setArticleRecyclerview(mAdapter);
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
