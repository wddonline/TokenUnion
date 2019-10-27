package com.tokenunion.pro.ui.capital.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tokenunion.pro.config.ApiUrl;
import com.tokenunion.pro.ui.base.BaseData;
import com.tokenunion.pro.ui.capital.model.SavingProduct;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.HttpConnectCallback;
import com.yidaichu.android.common.http.HttpManager;
import com.yidaichu.android.common.http.HttpRequestEntry;
import com.yidaichu.android.common.http.HttpResponseEntry;
import com.yidaichu.android.common.http.error.ErrorCode;
import com.yidaichu.android.common.http.error.HttpError;

import java.util.List;

public class SavingProductData extends BaseData {

    private final int PAGE_SIZE = 10;

    private SavingProductCallback mCallback;

    private int mPageNum = 1;

    public SavingProductData(ActivityFragmentActive active, SavingProductCallback callback) {
        super(active);
        this.mCallback = callback;
    }

    /*
        "limit": 0,
      "pageNo": 0,
      "symbol": "string",
      "type": 0
     */
    public void requestProducts(String symbol, String type, boolean isAppend) {
        if (!isAppend) {
            mPageNum = 1;
        }
        HttpRequestEntry requestEntry = buildRequest();
        requestEntry.setUrl(ApiUrl.BASE_URL + "/app/finance/product/page");
        requestEntry.addRequestParam("limit", PAGE_SIZE + "");
        requestEntry.addRequestParam("pageNo", mPageNum + "");
        requestEntry.addRequestParam("symbol", symbol);
        requestEntry.addRequestParam("type", type);
        HttpManager.getInstance().sendHttpRequest(mActive,
                requestEntry, new HttpConnectCallback() {

                    @Override
                    public void onRequestOk(HttpResponseEntry res) {
                        JSONObject json = JSON.parseObject((String) res.getData());
                        final List<SavingProduct> products = JSONArray.parseArray(json.getString("records"), SavingProduct.class);
                        runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if (products.size() > 0) {
                                    mCallback.onRequestProductsSuccess(isAppend, products);
                                    mPageNum++;
                                } else {
                                    mCallback.onRequestNoProducts(isAppend);
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
                                    mCallback.onNetworkError(isAppend);
                                } else {
                                    mCallback.onRequestProductsFailure(isAppend, error);
                                }
                            }
                        });
                    }
                });
    }

    public interface SavingProductCallback {

        void onNetworkError(boolean isAppend);
        void onRequestProductsFailure(boolean isAppend, HttpError error);
        void onRequestProductsSuccess(boolean isAppend, List<SavingProduct> products);
        void onRequestNoProducts(boolean isAppend);

    }
}
