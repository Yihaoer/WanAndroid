package com.yihaoer.wyh.wanandroid.mvp.ui.entity;

/**
 * Author: Yihaoer
 * Description: 首页banner的item
 */
public class BannerItem {
    private String desc;
    private String title;
    private String imagePath;
    private String url;
    private int type;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BannerItem{" +
                "desc='" + desc + '\'' +
                ", title='" + title + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                '}';
    }
}
