package com.yihaoer.wyh.wanandroid.mvp.ui.entity;

import java.util.List;

/**
 * Author: Yihaoer
 * Description: 导航页的数据item
 */
public class GuideItem {

    private String chapterName; //父标题
    private String link;
    private String title;

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "GuideItem{" +
                "chapterName='" + chapterName + '\'' +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
