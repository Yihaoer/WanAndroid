package com.yihaoer.wyh.wanandroid.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.mvp.ui.adapter.entity.HomeArticleItem;

import java.util.List;

/**
 * Author: Yihaoer
 * Description: 主页文章列表的适配器
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeArticleHolder> {

    private List<HomeArticleItem> list;
    private Context context;

    public HomeRecyclerViewAdapter(Context context, List<HomeArticleItem> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeArticleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HomeArticleHolder(LayoutInflater.from(context)
                .inflate(R.layout.home_article_item_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeArticleHolder homeArticleHolder, int i) {
        if (list.get(i).isFresh()){
            homeArticleHolder.articleFreshIv.setVisibility(View.VISIBLE);
        }else {
            homeArticleHolder.articleFreshIv.setVisibility(View.GONE);
        }
        homeArticleHolder.articleAuthorTv.setText(list.get(i).getAuthor());
        homeArticleHolder.articleTypeTv.setText(list.get(i).getType());
        homeArticleHolder.articleTitleTv.setText(list.get(i).getTitle());
        homeArticleHolder.articleDateTv.setText(list.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 自定义首页文章的ViewHolder
     */
    class HomeArticleHolder extends RecyclerView.ViewHolder {

        ImageView articleFreshIv; //"新"图标
        ImageView articleCollectIv; //"收藏"图标
        TextView articleAuthorTv; //文章作者名字
        TextView articleTypeTv; //文章类型
        TextView articleTitleTv; //文章标题
        TextView articleDateTv; //文章发表时间

        public HomeArticleHolder(@NonNull View itemView) {
            super(itemView);
            articleFreshIv = itemView.findViewById(R.id.new_icon_iv);
            articleCollectIv = itemView.findViewById(R.id.collection_iv);
            articleAuthorTv = itemView.findViewById(R.id.article_author_tv);
            articleTypeTv = itemView.findViewById(R.id.article_type_tv);
            articleTitleTv = itemView.findViewById(R.id.article_title_tv);
            articleDateTv = itemView.findViewById(R.id.article_date_tv);
        }
    }
}
