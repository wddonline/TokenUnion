package com.tokenunion.pro.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.Configs;
import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.ui.main.MainActivity;
import com.tokenunion.pro.R;
import com.tokenunion.pro.app.TUApplication;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.HttpConnectCallback;
import com.yidaichu.android.common.http.HttpManager;
import com.yidaichu.android.common.http.HttpRequestEntry;
import com.yidaichu.android.common.http.HttpResponseEntry;
import com.yidaichu.android.common.http.error.HttpError;

import static com.yidaichu.android.common.http.error.ErrorCode.NETWORK_ERROR;
import static com.yidaichu.android.common.http.error.ErrorCode.SERVER_ERROR;

/**
 * Create by: xiaohansong
 * Time: 2019-07-24 17:33
 * -
 * Description: 网络请求工具类
 */
public class NetRequestUtils {
    protected static void doRequest(ActivityFragmentActive tag, HttpRequestEntry requestEntry, Class clazz,
                                  final ApiRequestCallback callback){
//        requestEntry.setMethod(HttpRequestEntry.Method.POST);
        // 统一增加请求head
        if(UserAccount.getInstance().isLogin()) {
            requestEntry.addRequestHeader("token", UserAccount.getInstance().getUserBean().getToken());
        }
        requestEntry.addRequestHeader("language", Configs.getCurrentLanguage());

        HttpManager.getInstance().sendHttpRequest(tag,
                requestEntry, clazz, new HttpConnectCallback() {
                    @Override
                    public void onRequestOk(HttpResponseEntry res) {
                        callback.onSuccess(res.getData());
                    }

                    @Override
                    public void onRequestFailure(final HttpError error) {
//                        callback.onFailed(error.getErrorCode() +"", error.getErrorMsg());
                        processError(error, callback);
                    }
                });
    }

    protected static void doRequest(ActivityFragmentActive tag, HttpRequestEntry requestEntry,
                                  final ApiRequestCallback callback){
//        requestEntry.setMethod(HttpRequestEntry.Method.POST);
        // 统一增加请求head
        if(UserAccount.getInstance().isLogin()) {
            requestEntry.addRequestHeader("token", UserAccount.getInstance().getUserBean().getToken());
        }
        requestEntry.addRequestHeader("language", Configs.getCurrentLanguage());
        HttpManager.getInstance().sendHttpRequest(tag,
                requestEntry, new HttpConnectCallback() {
                    @Override
                    public void onRequestOk(HttpResponseEntry res) {
                        callback.onSuccess(res.getData());
                    }

                    @Override
                    public void onRequestFailure(final HttpError error) {
                        processError(error, callback);
                    }
                });
    }

    /**
     * 对错误统一处理（转化）
     * @param error
     * @param callback
     */
    protected static void processError(HttpError error, ApiRequestCallback callback){
        if(NETWORK_ERROR == error.getErrorCode()){
            // 网络无法连接
            if(null != MainActivity.getInstance()) {
                callback.onFailed(error.getErrorCode() + "",
                        MainActivity.getInstance().getString(R.string.no_connection_error));
            }else {
                callback.onFailed(error.getErrorCode() + "",
                        TUApplication.INSTANCE.getString(R.string.no_connection_error));
            }
        }else if(SERVER_ERROR == error.getErrorCode()){
            // 服务端错误
            callback.onFailed(error.getErrorCode() + "",
                    TUApplication.INSTANCE.getString(R.string.server_error));
        }else {
//            if(error.getErrorCode() == 500
            if(error.getErrorCode() == 401
                    && (null != error.getErrorMsg() && error.getErrorMsg().contains("token"))){
                BusinessUtils.loginTimeout();
            }else {
                // 业务错误
                callback.onFailed(error.getErrorCode() + "", error.getErrorMsg()+ "");
            }
        }
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isNetworkConnected(Context context) {
        boolean result = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
                if (networkInfos != null) {
                    final int length = networkInfos.length;
                    for (int i = 0; i < length; i++) {
                        if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
