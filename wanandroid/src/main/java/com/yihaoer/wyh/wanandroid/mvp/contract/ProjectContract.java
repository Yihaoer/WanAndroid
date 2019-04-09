package com.yihaoer.wyh.wanandroid.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.yihaoer.wyh.wanandroid.app.data.entity.project.ProjectTypeBean;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.ProjectTypeItem;

import java.util.List;

import io.reactivex.Observable;

public interface ProjectContract {

    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void showProjectTypeData(List<ProjectTypeItem> projectTypeBeanList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model extends IModel {
        Observable<ProjectTypeBean> getProjectTypeList(boolean refresh);

        List<ProjectTypeItem> parseProjectType(List<ProjectTypeBean.DataBean> dataBeanList);
    }
}
