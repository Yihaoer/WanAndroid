package com.yihaoer.wyh.wanandroid.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.mvp.ui.activity.WebviewActivity;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.GuideItem;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Author: Yihaoer
 * Description: 导航里文章标签列表的适配器
 */
public class GuideRecycleViewAdapter extends RecyclerView.Adapter<GuideRecycleViewAdapter.GuideArticleHolder> {

    private List<List<GuideItem>> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public GuideRecycleViewAdapter(Context context, List<List<GuideItem>> list) {
        this.mList = list;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public GuideArticleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GuideArticleHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.guide_article_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GuideArticleHolder guideArticleHolder, int i) {
        guideArticleHolder.title.setText(mList.get(i).get(0).getChapterName());
        guideArticleHolder.flowLayout.setAdapter(new TagAdapter<GuideItem>(mList.get(i)) {
            @Override
            public View getView(FlowLayout parent, int position, GuideItem o) {
                TextView tag = (TextView) mInflater.inflate(R.layout.guide_article_item_tag,parent,false);
                tag.setText(o.getTitle());
                return tag;
            }
        });
        //设置每个tag的点击事件
        guideArticleHolder.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(mContext.getApplicationContext(), WebviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //通过context跳转一定要加这个，否则报错
                intent.putExtra("url", mList.get(i).get(position).getLink());
                intent.putExtra("title",mList.get(i).get(position).getTitle());
                mContext.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class GuideArticleHolder extends RecyclerView.ViewHolder {

        TextView title;
        TagFlowLayout flowLayout;

        public GuideArticleHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.guide_title);
            flowLayout = itemView.findViewById(R.id.guide_flowlayout);
        }

    }
}
