package com.yihaoer.wyh.wanandroid.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.mvp.ui.activity.WebviewActivity;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.HomeArticleItem;

import java.util.List;

/**
 * Author: Yihaoer
 * Description: 主页文章列表的适配器
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeArticleHolder> {

    private static final int TYPE_HEADER = 0x11; //头部
    private static final int TYPE_NOMAL = 0x12; //正常数据
    private List<HomeArticleItem> mList;
    private View mHeaderView;
    private Context mContext;

    public HomeRecyclerViewAdapter(Context context, List<HomeArticleItem> list) {
        this.mList = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public HomeArticleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new HomeArticleHolder(mHeaderView);
        }
        return new HomeArticleHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.home_article_item_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeArticleHolder homeArticleHolder, int i) {
        if (getItemViewType(i) == TYPE_HEADER) {
            return;
        }

        int pos = getRealPosition(homeArticleHolder);
        HomeArticleItem articleItem = mList.get(pos);

        if (articleItem.isFresh()) {
            homeArticleHolder.articleFreshIv.setVisibility(View.VISIBLE);
        } else {
            homeArticleHolder.articleFreshIv.setVisibility(View.GONE);
        }
        homeArticleHolder.articleAuthorTv.setText(articleItem.getAuthor());
        homeArticleHolder.articleTypeTv.setText(articleItem.getType());
        homeArticleHolder.articleTitleTv.setText(articleItem.getTitle());
        homeArticleHolder.articleDateTv.setText(articleItem.getDate());

        //设置每个item的点击事件，点击后跳转到对应url的webview
        homeArticleHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), WebviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //通过context跳转一定要加这个，否则报错
                intent.putExtra("url", articleItem.getLink());
                intent.putExtra("title",articleItem.getTitle());
                mContext.getApplicationContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (getmHeaderView() != null) {
            return mList.size() + 1;
        }
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHeaderView != null) {
            return TYPE_HEADER;
        }
        return TYPE_NOMAL;
    }

    /**
     * 添加头部布局后的位置
     * headerView 不为空则 position - 1
     */
    public int getRealPosition(HomeArticleHolder homeArticleHolder) {
        int position = homeArticleHolder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    public View getmHeaderView() {
        return mHeaderView;
    }

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0); //插入下标0位置
    }

    public void addData(List<HomeArticleItem> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setData(List<HomeArticleItem> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
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
