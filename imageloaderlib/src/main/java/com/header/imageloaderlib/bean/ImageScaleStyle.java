package com.header.imageloaderlib.bean;

/**
 * Created by wyh on 2018/8/16.
 */

public enum ImageScaleStyle {
    /**
     * 图像不做调整
     */
    NONE,

    /**
     * 裁剪图片，当图片比ImageView大的时候，他把超过ImageView的部分裁剪掉，尽可能的让ImageView 完全填充，但图像可能不会全部显示
     */
    CENTERCROP,

    /**
     * 自适应ImageView的大小，并且会完整的显示图片在ImageView中，但是ImageView可能不会完全填充
     */
    FIT,

    /**
     *  图片会缩放到目标大小完全
     */
    SCALING
}

