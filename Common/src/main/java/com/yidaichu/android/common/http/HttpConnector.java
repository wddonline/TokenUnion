package com.yidaichu.android.common.http;

import com.yidaichu.android.common.http.error.HttpError;

/**
 * Created by wangdd on 16-11-26.
 */

public interface HttpConnector {

    HttpSession sendHttpRequest(ActivityFragmentActive host, HttpRequestEntry requestEntry, Class clazz, HttpConnectCallback callback);

    HttpResponseEntry sendSyncHttpRequest(ActivityFragmentActive host, HttpRequestEntry requestEntry, Class clazz) throws HttpError;

    HttpSession sendHttpRequest(ActivityFragmentActive host, HttpRequestEntry requestEntry, HttpConnectCallback callback);

    HttpResponseEntry sendSyncHttpRequest(ActivityFragmentActive host, HttpRequestEntry requestEntry) throws HttpError;

    void stopAllSession();

    void stopSessionByTag(Object tag);

}
