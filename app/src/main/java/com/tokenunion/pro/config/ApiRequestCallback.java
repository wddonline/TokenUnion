package com.tokenunion.pro.config;

/**
 * Create by hansongxiao
 */
public abstract class ApiRequestCallback {
    public abstract void onSuccess(Object object);
    public abstract void onFailed(String errCode, String errMessage);
}

