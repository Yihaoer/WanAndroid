package com.yihaoer.wyh.wanandroid.app.config.options;

import android.content.Context;

import com.jess.arms.di.module.ClientModule;
import com.yihaoer.wyh.wanandroid.BuildConfig;
import com.yihaoer.wyh.wanandroid.app.config.intercept.HttpLoggingInterceptor;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


public class MyRetrofitConfiguration implements ClientModule.RetrofitConfiguration {
    @Override
    public void configRetrofit(Context context, Retrofit.Builder builder) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(new HttpLoggingInterceptor());//使用自定义的Log拦截器
        }
        //        clientBuilder.addInterceptor(new UserAgentInterceptor());//使用自定义User-Agent
        builder.client(RetrofitUrlManager.getInstance().with(clientBuilder)
                .build());
    }
}
