package com.yihaoer.wyh.wanandroid.mvp.ui.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.header.imageloaderlib.agent.PictureLoader;
import com.yihaoer.wyh.wanandroid.R;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * Author: wuyihao
 * Description: 首页banner的holder
 */
public class BannerViewHolder implements MZViewHolder<String> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        mImageView = (ImageView) view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, String data) {
        // 数据绑定
        PictureLoader.getInstance()
                .load(data)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .into(mImageView);
    }
}