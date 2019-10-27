package com.tokenunion.pro.ui.capital.contact;

import com.tokenunion.pro.ui.base.BasePresenter;
import com.tokenunion.pro.ui.capital.data.SavingProductData;
import com.tokenunion.pro.ui.capital.model.SavingProduct;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.error.HttpError;

import java.util.List;

public class SavingProductPresenter implements BasePresenter, SavingProductData.SavingProductCallback {

    public interface View {
        void showNetworkErrorView(boolean isAppend);
        void showProductsFailureView(boolean isAppend, HttpError error);
        void showProductsSuccessView(boolean isAppend, List<SavingProduct> products);
        void showNoProductsView(boolean isAppend);
    }

    private View mView;
    private SavingProductData mData;

    public SavingProductPresenter(View view, ActivityFragmentActive active) {
        this.mView = view;
        mData = new SavingProductData(active, this);
    }

    public void getProducts(String symbol, String type, boolean isAppend) {
        mData.requestProducts(symbol, type, isAppend);
    }

    @Override
    public void onNetworkError(boolean isAppend) {
        mView.showNetworkErrorView(isAppend);
    }

    @Override
    public void onRequestProductsFailure(boolean isAppend, HttpError error) {
        mView.showProductsFailureView(isAppend, error);
    }

    @Override
    public void onRequestProductsSuccess(boolean isAppend, List<SavingProduct> products) {
        mView.showProductsSuccessView(isAppend, products);
    }

    @Override
    public void onRequestNoProducts(boolean isAppend) {
        mView.showNoProductsView(isAppend);
    }
}
