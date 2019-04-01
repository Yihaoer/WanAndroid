package com.yihaoer.wyh.wanandroid.mvp.ui.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.yihaoer.wyh.wanandroid.R;
import com.zhouwei.mzbanner.MZBannerView;

/**
 * Author: wuyihao
 * Description:
 */
public class BannerViewHolder extends MZBannerView<Integer> {
    private ImageView mImageView;

    public BannerViewHolder(@NonNull Context context) {
        super(context);
    }


}
