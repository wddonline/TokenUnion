package com.yidaichu.android.common.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangdd on 16-11-26.
 */

public class HttpRequestEntry {

    private final int TIME_OUT = 15000;

    public enum Method {
        POST,
        GET
    }

    public enum Format {
        JSON,
        FORM,
        FILE,
        MULTIPART
    }

    private Map<String, String> headers;
    private Map<String, Object> params;
    // 默认post
    private Method method = Method.POST;
    private String url;
    private String tag;
    private boolean shouldCached = false;

    private int timeOut = TIME_OUT;
    private Format format = Format.JSON;

    public HttpRequestEntry() {
        headers = new HashMap<>();
        params = new HashMap<>();
    }

    public void addRequestHeader(String headerKey, String headerVal) {
        headers.put(headerKey, headerVal);
    }

    public void addRequestHeaders(Map headers) {
        this.headers.putAll(headers);
    }

    public void addRequestParam(String paramKey, String paramVal) {
        params.put(paramKey, paramVal);
    }

    public void addRequestParams(Map params) {
        this.params.putAll(params);
    }

    public Map<String, String> getRequestHeaders() {
        return headers;
    }

    public Map<String, Object> getRequestParams() {
        return params;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setShouldCached(boolean shouldCached) {
        this.shouldCached = shouldCached;
    }

    public boolean shouldCache() {
        return shouldCached;
    }

}
