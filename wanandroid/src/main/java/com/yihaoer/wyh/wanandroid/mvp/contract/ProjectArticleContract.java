package com.yihaoer.wyh.wanandroid.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yihaoer.wyh.wanandroid.app.data.entity.project.ProjectArticleBean;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.ProjectRecycleViewAdapter;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.ProjectArticleItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Author: Yihaoer
 * Description:
 */
public interface ProjectArticleContract {

    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setArticleRecyclerview(ProjectRecycleViewAdapter adapter);

        //        void setArticleRecyclerview(List<ProjectArticleItem> list);
        void finishRefresh(int delayed);

        void finishLoadMore(int delayed);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel {
        Observable<ProjectArticleBean> getProjectArticleDataList(int pageId, String cid, boolean clearCache);

        List<ProjectArticleItem> parseProjectArticleData(List<ProjectArticleBean.DataBean.DatasBean> list);
    }
}
