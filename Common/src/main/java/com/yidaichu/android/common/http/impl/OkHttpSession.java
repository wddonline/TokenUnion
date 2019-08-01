package com.yidaichu.android.common.http.impl;

import com.yidaichu.android.common.http.HttpRequestEntry;
import com.yidaichu.android.common.http.HttpSession;

import okhttp3.Call;

/**
 * Created by wangdd on 16-11-26.
 */

public class OkHttpSession extends HttpSession {

    private Call call;
    private HttpRequestEntry requestEntry;

    public OkHttpSession(Call call, HttpRequestEntry requestEntry) {
        this.call = call;
        this.requestEntry = requestEntry;
    }

    @Override
    public void cancelRequest() {
        call.cancel();
    }

    @Override
    public String getRequestUrl() {
        return requestEntry.getUrl();
    }

    @Override
    public HttpRequestEntry getRequestEntry() {
        return requestEntry;
    }

}
