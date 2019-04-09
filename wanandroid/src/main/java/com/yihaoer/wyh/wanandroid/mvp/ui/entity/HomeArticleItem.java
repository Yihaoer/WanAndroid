package com.yihaoer.wyh.wanandroid.mvp.ui.entity;

/**
 * Author: wuyihao
 * Description: 首页文章的item
 */
public class HomeArticleItem {
    private String author; //作者名称
//    private String superChapterName; //一级分类名称
//    private String chapterName; //二级分类名称
    private String type; //文章类别
    private String link; //文章链接
    private String title; //文章标题
    private String date; //发表时间
    private boolean fresh; //是否带"新"图标

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String superChapterName, String chapterName) {
        this.type = superChapterName + " / " + chapterName;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }
}
