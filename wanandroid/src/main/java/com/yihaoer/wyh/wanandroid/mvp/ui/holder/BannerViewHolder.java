package com.yihaoer.wyh.wanandroid.mvp.ui.holder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.header.imageloaderlib.agent.PictureLoader;
import com.yihaoer.wyh.wanandroid.R;
import com.yihaoer.wyh.wanandroid.mvp.ui.activity.WebviewActivity;
import com.yihaoer.wyh.wanandroid.mvp.ui.entity.BannerItem;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * Author: wuyihao
 * Description: 首页banner的holder
 */
public class BannerViewHolder implements MZViewHolder<BannerItem> {

    private ImageView mImageView;
    private Context mContext;

    @Override
    public View createView(Context context) {
        mContext = context;
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        mImageView = (ImageView) view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, BannerItem bannerItem) {
        // 数据绑定
        PictureLoader.getInstance()
                .load(bannerItem.getImagePath())
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .into(mImageView);

        //设置每个图片的点击事件，点击后跳转到对应url的webview
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), WebviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //通过context跳转一定要加这个，否则报错
                intent.putExtra("url", bannerItem.getUrl());
                intent.putExtra("title",bannerItem.getTitle());
                mContext.getApplicationContext().startActivity(intent);
            }
        });
    }
}