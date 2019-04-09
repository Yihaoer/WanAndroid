package com.yihaoer.wyh.wanandroid.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectArticleContract;

import javax.inject.Inject;

/**
 * Author: Yihaoer
 * Description:
 */
public class ProjectArticleModel extends BaseModel implements ProjectArticleContract.Model {

    @Inject
    public ProjectArticleModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
