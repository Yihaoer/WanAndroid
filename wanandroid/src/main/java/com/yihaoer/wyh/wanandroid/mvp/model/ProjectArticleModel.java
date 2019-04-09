package com.yihaoer.wyh.wanandroid.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yihaoer.wyh.wanandroid.app.data.cache.ProjectCache;
import com.yihaoer.wyh.wanandroid.app.data.entity.project.ProjectArticleBean;
import com.yihaoer.wyh.wanandroid.app.data.entity.project.ProjectTypeBean;
import com.yihaoer.wyh.wanandroid.app.data.service.ProjectService;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectArticleContract;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.ProjectArticleItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;

/**
 * Author: Yihaoer
 * Description:
 */
public class ProjectArticleModel extends BaseModel implements ProjectArticleContract.Model {

    @Inject
    public ProjectArticleModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<ProjectArticleBean> getProjectArticleDataList(int pageId, String cid, boolean clearCache) {
        return mRepositoryManager.obtainCacheService(ProjectCache.class)
                .getProjectArticle(mRepositoryManager.obtainRetrofitService(ProjectService.class)
                        .getProjectArticleDataList(pageId, cid), new EvictProvider(false))
                .map(new Function<Reply<ProjectArticleBean>, ProjectArticleBean>() {
                    @Override
                    public ProjectArticleBean apply(Reply<ProjectArticleBean> projectArticleBeanReply) throws Exception {
                        return projectArticleBeanReply.getData();
                    }
                });
    }

    @Override
    public List<ProjectArticleItem> parseProjectArticleData(List<ProjectArticleBean.DataBean.DatasBean> list) {
        List<ProjectArticleItem> projectArticleItemList = new ArrayList<>();
        for (ProjectArticleBean.DataBean.DatasBean dataBean:list){
            ProjectArticleItem projectArticleItem = new ProjectArticleItem();
            projectArticleItem.setTitle(dataBean.getTitle());
            projectArticleItem.setDesc(dataBean.getDesc());
            projectArticleItem.setDate(dataBean.getNiceDate());
            projectArticleItem.setAuthor(dataBean.getAuthor());
            projectArticleItem.setEnvelopePic(dataBean.getEnvelopePic());
            projectArticleItemList.add(projectArticleItem);
        }
        return projectArticleItemList;
    }
}
