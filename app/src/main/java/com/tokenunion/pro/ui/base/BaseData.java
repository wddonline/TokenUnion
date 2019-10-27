package com.tokenunion.pro.ui.base;

import android.os.Handler;

import com.tokenunion.pro.config.Configs;
import com.tokenunion.pro.config.UserAccount;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.HttpRequestEntry;

public class BaseData {

    private Handler mHandler = new Handler();
    protected ActivityFragmentActive mActive;

    public BaseData( ActivityFragmentActive active) {
        this.mActive = active;
    }

    final protected HttpRequestEntry buildRequest() {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        if(UserAccount.getInstance().isLogin()) {
            requestEntry.addRequestHeader("token", UserAccount.getInstance().getUserBean().getToken());
        }
        requestEntry.addRequestHeader("language", Configs.getCurrentLanguage());
        return requestEntry;
    }

    public void runOnUIThread(Runnable runnable) {
        mHandler.post(runnable);
    }

}
