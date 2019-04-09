package com.yihaoer.wyh.wanandroid.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yihaoer.wyh.wanandroid.app.data.cache.ProjectCache;
import com.yihaoer.wyh.wanandroid.app.data.entity.project.ProjectTypeBean;
import com.yihaoer.wyh.wanandroid.app.data.service.ProjectService;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectContract;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.ProjectTypeItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;

public class ProjectModel extends BaseModel implements ProjectContract.Model {

    @Inject
    public ProjectModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 获取项目分类信息
     *
     * @param refresh
     * @return
     */
    @Override
    public Observable<ProjectTypeBean> getProjectTypeList(boolean refresh) {
        return mRepositoryManager.obtainCacheService(ProjectCache.class)
                .getProjectTypes(mRepositoryManager.obtainRetrofitService(ProjectService.class)
                        .getProjectTypes(), new EvictProvider(refresh))
                .map(new Function<Reply<ProjectTypeBean>, ProjectTypeBean>() {
                    @Override
                    public ProjectTypeBean apply(Reply<ProjectTypeBean> projectTypeBeanReply) throws Exception {
                        return projectTypeBeanReply.getData();
                    }
                });
    }

    /**
     * 解析项目分类里的数据并存储到集合
     *
     * @param dataBeanList
     * @return
     */
    @Override
    public List<ProjectTypeItem> parseProjectType(List<ProjectTypeBean.DataBean> dataBeanList) {
        List<ProjectTypeItem> list = new ArrayList<>();
        for (ProjectTypeBean.DataBean dataBean : dataBeanList) {
            ProjectTypeItem projectTypeItem = new ProjectTypeItem();
            projectTypeItem.setId(dataBean.getId());
            projectTypeItem.setName(dataBean.getName());
            list.add(projectTypeItem);
        }
        return list;
    }
}
