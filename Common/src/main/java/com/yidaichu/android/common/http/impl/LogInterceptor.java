package com.yidaichu.android.common.http.impl;

import com.yidaichu.android.common.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LogInterceptor implements Interceptor {
    String TAG = LogInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long tmStart = System.nanoTime();
        LogUtil.d(TAG, String.format("Sending request %s", request.url()));

        Response response = chain.proceed(request);

        long tmEnd = System.nanoTime();
        LogUtil.d(TAG, String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (tmEnd - tmStart) / 1e6d, response.headers()));

        return response;
    }
}