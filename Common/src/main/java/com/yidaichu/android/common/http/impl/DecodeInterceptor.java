package com.yidaichu.android.common.http.impl;

import com.yidaichu.android.common.utils.LogUtil;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Create by hsxiao. 2018/8/10
 */
public class DecodeInterceptor implements Interceptor {
    private static final String TAG = DecodeInterceptor.class.getSimpleName();
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public DecodeInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = String.valueOf(request.url());

        long beginRequestTime = System.currentTimeMillis();
//        LogUtil.d(DecodeInterceptor.class.getSimpleName(), url + "请求开始的时间" + TimeUtils.getDateToString(beginRequestTime, TIME_TYPE_06));

        Response originalResponse = null;// = chain.proceed(request);
        try {
            LogUtil.d(TAG, "--> Request: "+ url);
            originalResponse = chain.proceed(request);
//            LogUtil.d(TAG, "<-- Request: "+ url);
        } catch (Exception e) {
            LogUtil.e(TAG, "<-- HTTP FAILED: " + e+ "\n"+ url);
            throw e;
        }
        if (null != originalResponse) {
            ResponseBody responseBody = originalResponse.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            String bodyString = buffer.clone().readString(charset);
            LogUtil.d(TAG, "<-- Response: \n"+ bodyString+ "\n"+ url);
            // TODO: 如果需要解密，在这里做
//            String decodeString = bodyString;
            ResponseBody responseBodyDecode = ResponseBody.create(contentType, bodyString);

            return originalResponse.newBuilder().body(responseBodyDecode).build();
        } else {
            return null;
        }
    }

}
