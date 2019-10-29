package com.tokenunion.pro.ui.capital.contact;

import com.tokenunion.pro.ui.base.BasePresenter;
import com.tokenunion.pro.ui.capital.data.WealthRecordData;
import com.tokenunion.pro.ui.capital.model.WealthRecord;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.error.HttpError;

import java.util.List;

public class WealthRecordPresenter implements BasePresenter, WealthRecordData.WealthRecordCallback {

    public interface View {
        void showNetworkErrorView(boolean isAppend);
        void showRecordsFailureView(boolean isAppend, HttpError error);
        void showRecordsSuccessView(boolean isAppend, List<WealthRecord> records);
        void showNoRecordsView(boolean isAppend);
    }

    private View mView;
    private WealthRecordData mData;

    public WealthRecordPresenter(View view, ActivityFragmentActive active) {
        this.mView = view;
        mData = new WealthRecordData(active, this);
    }

    public void getRecords(String symbol, String type, boolean isAppend) {
        mData.requestRecords(symbol, type, isAppend);
    }

    @Override
    public void onNetworkError(boolean isAppend) {
        mView.showNetworkErrorView(isAppend);
    }

    @Override
    public void onRequestRecordsFailure(boolean isAppend, HttpError error) {
        mView.showRecordsFailureView(isAppend, error);
    }

    @Override
    public void onRequestRecordsSuccess(boolean isAppend, List<WealthRecord> records) {
        mView.showRecordsSuccessView(isAppend, records);
    }

    @Override
    public void onRequestNoRecords(boolean isAppend) {
        mView.showNoRecordsView(isAppend);
    }
}
