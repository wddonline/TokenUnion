package com.tokenunion.pro.ui.find.contact;

import com.tokenunion.pro.ui.base.BasePresenter;
import com.tokenunion.pro.ui.find.data.FindHomeData;
import com.tokenunion.pro.ui.find.model.FindBanner;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.error.HttpError;

import java.util.List;

public class FindHomePresenter implements BasePresenter, FindHomeData.FindHomeCallback {

    public interface View {
        void showNetworkErrorView();
        void showBannerFailureView(HttpError error);
        void showBannerSuccessView(List<FindBanner> records);
        void showNoBannerView();
    }

    private View mView;
    private FindHomeData mData;

    public FindHomePresenter(View view, ActivityFragmentActive active) {
        this.mView = view;
        mData = new FindHomeData(active, this);
    }

    public void getBanners() {
        mData.requestBanners();
    }

    @Override
    public void onNetworkError() {
        mView.showNetworkErrorView();
    }

    @Override
    public void onRequestBannerFailure(HttpError error) {
        mView.showBannerFailureView(error);
    }

    @Override
    public void onRequestBannerSuccess(List<FindBanner> banners) {
        mView.showBannerSuccessView(banners);
    }

    @Override
    public void onRequestNoBanner() {
        mView.showNoBannerView();
    }
}
