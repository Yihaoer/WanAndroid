package com.yihaoer.wyh.wanandroid.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yihaoer.wyh.wanandroid.mvp.contract.ProjectContract;

import javax.inject.Inject;

public class ProjectModel extends BaseModel implements ProjectContract.Model {

    @Inject
    public ProjectModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
