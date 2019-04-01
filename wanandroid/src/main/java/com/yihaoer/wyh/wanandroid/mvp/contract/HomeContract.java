package com.yihaoer.wyh.wanandroid.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yihaoer.wyh.wanandroid.app.data.entity.home.BannerBean;
import com.yihaoer.wyh.wanandroid.app.data.entity.home.HomeArticleBean;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.HomeRecyclerViewAdapter;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.entity.BannerItem;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.entity.HomeArticleItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public interface HomeContract {

    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setBanner(List<String> list);
        void setArticleRecyclerview(List<HomeArticleItem> list);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel {
        Observable<BannerBean> getBannerDataList(boolean refresh);

        List<BannerItem> parseBannerData(List<BannerBean.DataBean> dataList);

        Observable<HomeArticleBean> getHomeArticleDataList(int pageId, boolean clearCache);

        List<HomeArticleItem> parseHomeArticleData(List<HomeArticleBean.DataBean.DatasBean> datasList);
    }
}
