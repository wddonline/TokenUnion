package com.tokenunion.pro.ui.capital.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tokenunion.pro.config.ApiUrl;
import com.tokenunion.pro.ui.base.BaseData;
import com.tokenunion.pro.ui.capital.model.WealthRecord;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.HttpConnectCallback;
import com.yidaichu.android.common.http.HttpManager;
import com.yidaichu.android.common.http.HttpRequestEntry;
import com.yidaichu.android.common.http.HttpResponseEntry;
import com.yidaichu.android.common.http.error.ErrorCode;
import com.yidaichu.android.common.http.error.HttpError;

import java.util.List;

public class WealthRecordData extends BaseData {

    private final int PAGE_SIZE = 10;

    private WealthRecordCallback mCallback;

    private int mPageNum = 1;

    public WealthRecordData(ActivityFragmentActive active, WealthRecordCallback callback) {
        super(active);
        this.mCallback = callback;
    }

    /*
       {
          "limit": 0,
          "orderType": 0,
          "pageNo": 0,
          "symbol": "string"
        }
     */
    public void requestRecords(String symbol, String orderType, boolean isAppend) {
        if (!isAppend) {
            mPageNum = 1;
        }
        HttpRequestEntry requestEntry = buildRequest();
        requestEntry.setUrl(ApiUrl.BASE_URL + "/app/finance/orders/page");
        requestEntry.addRequestParam("limit", PAGE_SIZE + "");
        requestEntry.addRequestParam("pageNo", mPageNum + "");
        requestEntry.addRequestParam("symbol", symbol);
        requestEntry.addRequestParam("orderType", orderType);
        HttpManager.getInstance().sendHttpRequest(mActive,
                requestEntry, new HttpConnectCallback() {

                    @Override
                    public void onRequestOk(HttpResponseEntry res) {
                        JSONObject json = JSON.parseObject((String) res.getData());
                        final List<WealthRecord> records = JSONArray.parseArray(json.getString("orders"), WealthRecord.class);
                        runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if (records.size() > 0) {
                                    mCallback.onRequestRecordsSuccess(isAppend, records);
                                    mPageNum++;
                                } else {
                                    mCallback.onRequestNoRecords(isAppend);
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
                                    mCallback.onRequestRecordsFailure(isAppend, error);
                                }
                            }
                        });
                    }
                });
    }

    public interface WealthRecordCallback {

        void onNetworkError(boolean isAppend);
        void onRequestRecordsFailure(boolean isAppend, HttpError error);
        void onRequestRecordsSuccess(boolean isAppend, List<WealthRecord> records);
        void onRequestNoRecords(boolean isAppend);

    }
}
