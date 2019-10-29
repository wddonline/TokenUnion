package com.tokenunion.pro.ui.find.data;

import com.tokenunion.pro.config.ApiUrl;
import com.tokenunion.pro.ui.base.BaseData;
import com.tokenunion.pro.ui.find.model.FindBanner;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.HttpConnectCallback;
import com.yidaichu.android.common.http.HttpManager;
import com.yidaichu.android.common.http.HttpRequestEntry;
import com.yidaichu.android.common.http.HttpResponseEntry;
import com.yidaichu.android.common.http.error.ErrorCode;
import com.yidaichu.android.common.http.error.HttpError;

import java.util.List;

public class FindHomeData extends BaseData {

    private FindHomeCallback mCallback;

    public FindHomeData(ActivityFragmentActive active, FindHomeCallback callback) {
        super(active);
        this.mCallback = callback;
    }

    public void requestBanners() {
        HttpRequestEntry requestEntry = buildRequest();
        requestEntry.setUrl(ApiUrl.BASE_URL + "/app/banner/explore");
        HttpManager.getInstance().sendHttpRequest(mActive, requestEntry, FindBanner.class, new HttpConnectCallback() {

                    @Override
                    public void onRequestOk(HttpResponseEntry res) {
                        final List<FindBanner> banners = ((List<FindBanner>) res.getData());
                        runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if (banners.size() > 0) {
                                    mCallback.onRequestBannerSuccess(banners);
                                } else {
                                    mCallback.onRequestNoBanner();
                                }
                            }
                        });
                    }

                    @Override
                    public void onRequestFailure(final HttpError error) {
                        runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if (error.getErrorCode() == ErrorCode.NO_CONNECTION_ERROR) {
                                    mCallback.onNetworkError();
                                } else {
                                    mCallback.onRequestBannerFailure(error);
                                }
                            }
                        });
                    }
                });
    }

    public interface FindHomeCallback {

        void onNetworkError();
        void onRequestBannerFailure(HttpError error);
        void onRequestBannerSuccess(List<FindBanner> banners);
        void onRequestNoBanner();

    }
}
