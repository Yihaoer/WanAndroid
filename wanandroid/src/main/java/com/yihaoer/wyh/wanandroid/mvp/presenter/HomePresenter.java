package com.yihaoer.wyh.wanandroid.mvp.presenter;

import android.util.Log;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yihaoer.wyh.wanandroid.app.data.entity.home.BannerBean;
import com.yihaoer.wyh.wanandroid.app.data.entity.home.HomeArticleBean;
import com.yihaoer.wyh.wanandroid.mvp.contract.HomeContract;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.entity.BannerItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import retrofit2.adapter.rxjava2.HttpException;

public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View view) {
        super(model, view);
    }

    /**
     * 加载首页banner的数据
     *
     * @param refresh 是否刷新
     */
    public void loadBannerData(boolean refresh) {
        mModel.getBannerDataList(refresh)
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
                        List<BannerItem> list = mModel.parseBannerData(bannerBean.getData());
                        List<String> imgList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            imgList.add(list.get(i).getImagePath());
                        }
                        mRootView.setBanner(imgList);
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
    public void loadHomeArticleData(int pageId, boolean clearCache) {
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

                    }

                    @Override
                    public void onNext(HomeArticleBean homeArticleBean) {
                        if (homeArticleBean != null) {
                            //设置首页文章列表
                            mRootView.setArticleRecyclerview(mModel.parseHomeArticleData(homeArticleBean.getData().getDatas()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("adsad",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
