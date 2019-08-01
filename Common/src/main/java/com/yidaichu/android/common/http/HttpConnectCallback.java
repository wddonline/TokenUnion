package com.yidaichu.android.common.http;

import com.yidaichu.android.common.http.error.HttpError;

/**
 * Created by wangdd on 16-11-26.
 */

public interface HttpConnectCallback {

    void onRequestOk(HttpResponseEntry response);
    void onRequestFailure(HttpError error);

}
