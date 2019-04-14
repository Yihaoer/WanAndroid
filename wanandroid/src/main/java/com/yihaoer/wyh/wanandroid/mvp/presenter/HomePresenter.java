package com.yihaoer.wyh.wanandroid.mvp.presenter;

import android.app.Application;
import android.content.Context;

import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yihaoer.wyh.wanandroid.app.data.entity.home.BannerBean;
import com.yihaoer.wyh.wanandroid.app.data.entity.home.HomeArticleBean;
import com.yihaoer.wyh.wanandroid.mvp.contract.HomeContract;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.HomeRecyclerViewAdapter;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.BannerItem;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.HomeArticleItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {

    private HomeRecyclerViewAdapter mHomeRecyclerViewAdapter;
    private AppManager mAppManager;
    private Application mApplication;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View view, AppManager appManager, Application application) {
        super(model, view);
        this.mAppManager = appManager;
        this.mApplication = application;

    }

    /**
     * 加载首页banner的数据
     *
     * @param clearCache 是否刷新
     */
    public void loadBannerData(boolean clearCache) {
        mModel.getBannerDataList(clearCache)
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
//                        List<BannerItem> list = mModel.parseBannerData(bannerBean.getData());
//                        List<String> imgList = new ArrayList<>();
//                        for (int i = 0; i < list.size(); i++) {
//                            imgList.add(list.get(i).getImagePath());
//                        }
                        mRootView.setBanner(mModel.parseBannerData(bannerBean.getData()));
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
     * 加载首页文章数据
     *
     * @param pageId
     * @param clearCache
     */
    public void loadHomeArticleData(int pageId, boolean refresh, boolean clearCache) {
        mModel.getHomeArticleDataList(pageId, clearCache)
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<HomeArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mRootView.hideLoading();
                        if (refresh){
                            mRootView.finishRefresh(1000);
                        }else {
                            mRootView.finishLoadMore(1000);
                        }
                    }

                    @Override
                    public void onNext(HomeArticleBean homeArticleBean) {
                        if (homeArticleBean != null) {
                            //设置首页文章列表
                            //                            mRootView.setArticleRecyclerview(mModel.parseHomeArticleData(homeArticleBean.getData().getDatas()));
                            setArticleRecyclerViewAdapter(mModel.parseHomeArticleData(homeArticleBean.getData().getDatas()), refresh);
                        }
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
     * @param list 数据集合
     * @param refresh 是否是下拉刷新？   true：下拉刷新    false：上拉加载
     */
    public void setArticleRecyclerViewAdapter(List<HomeArticleItem> list, boolean refresh) {
        if (mHomeRecyclerViewAdapter == null) {
            mHomeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext(), list);
            mRootView.setArticleRecyclerview(mHomeRecyclerViewAdapter);
        } else {
            if (refresh) {
                mHomeRecyclerViewAdapter.setData(list);
            }else {
                mHomeRecyclerViewAdapter.addData(list);
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
