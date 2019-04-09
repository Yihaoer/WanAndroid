package com.yihaoer.wyh.wanandroid.mvp.ui.entity;

/**
 * Author: Yihaoer
 * Description: 项目文章的item
 */
public class ProjectArticleItem {

    private String envelopePic; //封面图
    private String title; //文章标题
    private String desc; //文章描述
    private String date; //发布时间
    private String author; //作者
    private String link; //链接

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
