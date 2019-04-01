package com.yihaoer.wyh.wanandroid.mvp.model;

import android.util.Log;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yihaoer.wyh.wanandroid.app.data.Api;
import com.yihaoer.wyh.wanandroid.app.data.cache.HomeCache;
import com.yihaoer.wyh.wanandroid.app.data.entity.home.BannerBean;
import com.yihaoer.wyh.wanandroid.app.data.entity.home.HomeArticleBean;
import com.yihaoer.wyh.wanandroid.app.data.service.HomeService;
import com.yihaoer.wyh.wanandroid.mvp.contract.HomeContract;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.entity.BannerItem;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.entity.HomeArticleItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class HomeModel extends BaseModel implements HomeContract.Model {

    //存放首页文章数据的集合
    private List<HomeArticleItem> mHomeArticleItemLists = new ArrayList<>();

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        RetrofitUrlManager.getInstance().putDomain("home", Api.WANANDROID_URL);
    }

    /**
     * 获取首页banner数据
     *
     * @param refresh
     * @return
     */
    @Override
    public Observable<BannerBean> getBannerDataList(boolean refresh) {
//                return mRepositoryManager
//                        .obtainRetrofitService(HomeService.class)
//                        .getBannerDataList();
        return mRepositoryManager
                .obtainCacheService(HomeCache.class)
                .getBannerDataList(mRepositoryManager
                        .obtainRetrofitService(HomeService.class)
                        .getBannerDataList(), new EvictProvider(refresh))
                .map(new Function<Reply<BannerBean>, BannerBean>() {
                    @Override
                    public BannerBean apply(Reply<BannerBean> bannerBeanReply) throws Exception {
//                        Log.i("adsad","banner" + bannerBeanReply.getData().getData().toString());
                        return bannerBeanReply.getData();
                    }
                });
    }

    /**
     * 解析首页banner数据并存储到集合
     *
     * @param dataList
     * @return
     */
    @Override
    public List<BannerItem> parseBannerData(List<BannerBean.DataBean> dataList) {
        List<BannerItem> list = new ArrayList<>();
        for (BannerBean.DataBean bannerData : dataList) {
            Log.i("sdasds",dataList.toString());
            BannerItem item = new BannerItem();
            item.setDesc(bannerData.getDesc());
            item.setImagePath(bannerData.getImagePath());
            item.setTitle(bannerData.getTitle());
            item.setType(bannerData.getType());
            item.setUrl(bannerData.getUrl());
            list.add(item);
        }
        return list;
    }

    /**
     * 获取首页文章数据
     *
     * @param pageId     页码
     * @param clearCache 是否清除缓存
     * @return
     */
    @Override
    public Observable<HomeArticleBean> getHomeArticleDataList(int pageId, boolean clearCache) {
        return mRepositoryManager.obtainRetrofitService(HomeService.class)
                .getHomeArticleDataList(pageId);
//        return mRepositoryManager.obtainCacheService(HomeCache.class)
//                .getHomeArticleDataList(mRepositoryManager.obtainRetrofitService(HomeService.class)
//                        .getHomeArticleDataList(pageId), new DynamicKey(pageId), new EvictProvider(clearCache))
//                .map(new Function<Reply<HomeArticleBean>, HomeArticleBean>() {
//                    @Override
//                    public HomeArticleBean apply(Reply<HomeArticleBean> homeArticleBeanReply) throws Exception {
//                        Log.i("adsad",homeArticleBeanReply.getData().getData().toString());
//                        return homeArticleBeanReply.getData();
//                    }
//                });
    }

    /**
     * 解析首页文章数据并存储到集合
     *
     * @param datasList
     * @return
     */
    @Override
    public List<HomeArticleItem> parseHomeArticleData(List<HomeArticleBean.DataBean.DatasBean> datasList) {
        for (HomeArticleBean.DataBean.DatasBean datas : datasList) {
            HomeArticleItem homeArticleItem = new HomeArticleItem();
            homeArticleItem.setAuthor(datas.getAuthor());
            homeArticleItem.setDate(datas.getNiceDate());
            homeArticleItem.setLink(datas.getLink());
            homeArticleItem.setTitle(datas.getTitle());
            homeArticleItem.setType(datas.getSuperChapterName(), datas.getChapterName());
            //判断是否加"新"图标
            if (datas.isFresh()) {
                homeArticleItem.setFresh(true);
            } else {
                homeArticleItem.setFresh(false);
            }
            mHomeArticleItemLists.add(homeArticleItem);
        }
        return mHomeArticleItemLists;
    }
}
