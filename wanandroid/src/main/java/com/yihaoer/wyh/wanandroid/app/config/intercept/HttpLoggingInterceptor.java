package com.yihaoer.wyh.wanandroid.app.config.intercept;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * 创建日志拦截器
 *
 * @return
 */
public class HttpLoggingInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d("OkHttp", "HttpHelper1" + String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        okhttp3.Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        Log.d("OkHttp", "HttpHelper2" + String.format("Received response for %s in %.1fms%n%s%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers(), response.toString()));
        return response;
    }
}
