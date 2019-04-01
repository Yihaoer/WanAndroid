package com.yihaoer.wyh.wanandroid.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yihaoer.wyh.wanandroid.mvp.contract.HierarchyContract;

import javax.inject.Inject;

public class HierarchyModel extends BaseModel implements HierarchyContract.Model {

    @Inject
    public HierarchyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
