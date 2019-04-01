package com.yihaoer.wyh.wanandroid.mvp.model;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.yihaoer.wyh.wanandroid.mvp.contract.GuideContract;

import javax.inject.Inject;

public class GuideModel extends BaseModel implements GuideContract.Model {

    @Inject
    public GuideModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
