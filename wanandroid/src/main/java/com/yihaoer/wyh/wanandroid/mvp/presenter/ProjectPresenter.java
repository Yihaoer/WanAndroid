package com.yihaoer.wyh.wanandroid.mvp.presenter;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.yihaoer.wyh.wanandroid.app.data.entity.project.ProjectTypeBean;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectContract;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.ProjectTypeItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

public class ProjectPresenter extends BasePresenter<ProjectContract.Model, ProjectContract.View> {

    @Inject
    public ProjectPresenter(ProjectContract.Model model, ProjectContract.View view) {
        super(model, view);
    }

    /**
     * 加载项目分类的数据
     *
     * @param refresh 是否刷新
     */
    public void loadProjectTypes(boolean refresh){
        mModel.getProjectTypeList(refresh)
                .retryWhen(new RetryWithDelay(3,2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<ProjectTypeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ProjectTypeBean projectTypeBean) {
                        List<ProjectTypeItem> projectTypeItemList = mModel.parseProjectType(projectTypeBean.getData());
                        mRootView.showProjectTypeData(projectTypeItemList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
