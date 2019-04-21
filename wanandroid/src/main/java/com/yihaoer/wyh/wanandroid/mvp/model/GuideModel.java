package com.yihaoer.wyh.wanandroid.mvp.model;

import android.content.ClipData;
import android.util.Log;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yihaoer.wyh.wanandroid.app.data.cache.GuideCache;
import com.yihaoer.wyh.wanandroid.app.data.entity.guide.GuideBean;
import com.yihaoer.wyh.wanandroid.app.data.service.GuideService;
import com.yihaoer.wyh.wanandroid.mvp.contract.GuideContract;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.GuideItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;

public class GuideModel extends BaseModel implements GuideContract.Model {

    private List<List<GuideItem>> guideItemList; //存放不同类别文章集合的集合
    private List<GuideItem> guideItems; //存放同个类别文章

    @Inject
    public GuideModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 获取导航页数据
     *
     * @param clearCache
     * @return
     */
    @Override
    public Observable<GuideBean> getGuideDataList(boolean clearCache) {
        return mRepositoryManager
                .obtainCacheService(GuideCache.class)
                .getGuideDataList(mRepositoryManager
                        .obtainRetrofitService(GuideService.class)
                        .getGuideDataList(), new EvictProvider(clearCache))
                .map(new Function<Reply<GuideBean>, GuideBean>() {
                    @Override
                    public GuideBean apply(Reply<GuideBean> guideBeanReply) throws Exception {
                        return guideBeanReply.getData();
                    }
                });
    }

    /**
     * 解析导航数据并存储到集合
     *
     * @param list
     * @return 包含导航里所有数据的集合
     */
    @Override
    public List<List<GuideItem>> parseGuideData(List<GuideBean.DataBean> list) {
        guideItemList = new ArrayList<>();
        if (!list.isEmpty()) {
            for (int k = 0; k < list.size(); k++) {
                guideItems = new ArrayList<>();
                for (int i = 0; i < list.get(k).getArticles().size(); i++) {
                    GuideItem item = new GuideItem();
                    item.setChapterName(list.get(k).getArticles().get(i).getChapterName());
                    item.setLink(list.get(k).getArticles().get(i).getLink());
                    item.setTitle(list.get(k).getArticles().get(i).getTitle());
                    guideItems.add(item);
                }
                guideItemList.add(guideItems);
            }
        }
        return guideItemList;
    }
}
