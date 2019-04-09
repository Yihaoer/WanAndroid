package com.yihaoer.wyh.wanandroid.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.header.imageloaderlib.agent.PictureLoader;
import com.jess.arms.http.imageloader.ImageLoader;
import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.HomeArticleItem;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.ProjectArticleItem;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Author: Yihaoer
 * Description: 项目文章列表的适配器
 */
public class ProjectRecycleViewAdapter extends RecyclerView.Adapter<ProjectRecycleViewAdapter.ProjectArticleHolder> {

    private List<ProjectArticleItem> projectArticleItemList;
    private Context mContext;

    public ProjectRecycleViewAdapter(Context context,List<ProjectArticleItem> projectArticleItemList){
        this.mContext = context;
        this.projectArticleItemList = projectArticleItemList;
    }

    public void addData(List<ProjectArticleItem> list) {
        projectArticleItemList.addAll(list);
        notifyDataSetChanged();
    }

    public void setData(List<ProjectArticleItem> list){
        projectArticleItemList.clear();
        projectArticleItemList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectArticleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProjectArticleHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.project_article_item_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectArticleHolder projectArticleHolder, int i) {
        ProjectArticleItem projectArticleItem = projectArticleItemList.get(i);
        projectArticleHolder.articleTitleTv.setText(projectArticleItem.getTitle());
        projectArticleHolder.articleDescTv.setText(projectArticleItem.getDesc());
        projectArticleHolder.articleDateTv.setText(projectArticleItem.getDate());
        projectArticleHolder.articleAuthorTv.setText(projectArticleItem.getAuthor());
        PictureLoader.getInstance()
                .load(projectArticleItem.getEnvelopePic())
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .into(projectArticleHolder.articleEnvelopePicIv);
    }

    @Override
    public int getItemCount() {
        return projectArticleItemList.size();
    }

    /**
     * 自定义首页文章的ViewHolder
     */
    class ProjectArticleHolder extends RecyclerView.ViewHolder {

        ImageView articleCollectIv; //"收藏"图标
        ImageView articleEnvelopePicIv; //文章封面
        TextView articleTitleTv; //文章标题
        TextView articleDescTv; //文章描述
        TextView articleDateTv; //文章发表时间
        TextView articleAuthorTv; //文章作者名字

        public ProjectArticleHolder(@NonNull View itemView) {
            super(itemView);
            articleCollectIv = itemView.findViewById(R.id.collection_iv);
            articleEnvelopePicIv = itemView.findViewById(R.id.article_envelope_pic);
            articleTitleTv = itemView.findViewById(R.id.article_title_tv);
            articleDescTv = itemView.findViewById(R.id.article_desc_tv);
            articleDateTv = itemView.findViewById(R.id.article_date_tv);
            articleAuthorTv = itemView.findViewById(R.id.article_author_tv);
        }
    }
}
