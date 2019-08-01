package com.yidaichu.android.common.http;

/**
 * Created by wangdd on 16-11-26.
 */

public abstract class HttpSession {

    protected String tag;

    public abstract void cancelRequest();

    public abstract String getRequestUrl();

    public abstract HttpRequestEntry getRequestEntry();

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
