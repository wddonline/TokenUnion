package com.yidaichu.android.common.http;

import android.util.Log;

import com.yidaichu.android.common.http.error.HttpError;
import com.yidaichu.android.common.http.impl.OkHttpConnector;

/**
 * Created by wangdd on 16-11-26.
 */

public class HttpManager {
    private final String TAG = "HttpManager";

    private static HttpManager INSTANCE;

    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    private HttpConnector connecter;

    private HttpManager() {
        connecter = new OkHttpConnector();
    }

    public void setConnecter(HttpConnector connecter) {
        this.connecter = connecter;
    }

    public HttpSession sendHttpRequest(ActivityFragmentActive host, HttpRequestEntry request, Class clazz, HttpConnectCallback callback) {
        if (connecter == null) {
            Log.e(TAG, "A HttpConnector getInstance isn\'t setted for HttpManager");
            return null;
        }
        return connecter.sendHttpRequest(host, request, clazz, callback);
    }

    public HttpResponseEntry sendSyncHttpRequest(ActivityFragmentActive host, HttpRequestEntry request, Class clazz, HttpConnectCallback callback) throws HttpError {
        if (connecter == null) {
            Log.e(TAG, "A HttpConnector getInstance isn\'t setted for HttpManager");
            return null;
        }
        return connecter.sendSyncHttpRequest(host, request, clazz);
    }

    public HttpSession sendHttpRequest(ActivityFragmentActive host, HttpRequestEntry request, HttpConnectCallback callback) {
        if (connecter == null) {
            Log.e(TAG, "A HttpConnector getInstance isn\'t setted for HttpManager");
            return null;
        }
        return connecter.sendHttpRequest(host, request, callback);
    }

    public HttpResponseEntry sendSyncHttpRequest(ActivityFragmentActive host, HttpRequestEntry request, HttpConnectCallback callback) throws HttpError {
        if (connecter == null) {
            Log.e(TAG, "A HttpConnector getInstance isn\'t setted for HttpManager");
            return null;
        }
        return connecter.sendSyncHttpRequest(host, request);
    }

    public void stopAllSession() {
        if (connecter == null) {
            Log.e(TAG, "A HttpConnector getInstance isn\'t setted for HttpManager");
            return;
        }
        connecter.stopAllSession();
    }

    public void stopSessionByTag(String tag) {
        if (connecter == null) {
            Log.e(TAG, "A HttpConnector getInstance isn\'t setted for HttpManager");
            return;
        }
        connecter.stopSessionByTag(tag);
    }

}
