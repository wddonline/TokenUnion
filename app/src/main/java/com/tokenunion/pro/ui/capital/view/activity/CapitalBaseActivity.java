package com.tokenunion.pro.ui.capital.view.activity;

import com.tokenunion.pro.ui.capital.model.AssetInfo;
import com.tokenunion.pro.ui.capital.model.AssetOverviewBean;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.ui.capital.model.ProfitListBean;
import com.tokenunion.pro.utils.ToastUtils;

/**
 * Create by: xiaohansong
 * Time: 2019-07-27 18:32
 * -
 * Description:
 */
public abstract class CapitalBaseActivity extends BaseActivity {
    /**
     * 用户持有资产概览，顶部的数据
     */
    protected AssetOverviewBean mAssetOverviewBean;
    /**
     *
     * 理财产品详情
     */
    protected AssetInfo mAssetInfo;

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

    }

    protected abstract void updateData();
    protected abstract void updateOverviewData();

    /**
     * 顶部的总收益
     */
    protected void requestOverviewData(String currency){
        CapitalApi.getFinanceAssetOverview(currency, mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mAssetOverviewBean = (AssetOverviewBean)object;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateOverviewData();
                    }
                });

            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(CapitalBaseActivity.this, errMessage);
                    }
                });
            }
        });
    }

    /**
     * 产品信息
     */
    protected void requestAssetInfo(String symbol, int prodId) {
//        if (null == mAssetOverviewBean) {
//            return;
//        }
        CapitalApi.getFinanceAssetInfo(symbol, prodId, mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        mAssetInfo = (AssetInfo) object;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateData();
                            }
                        });
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(CapitalBaseActivity.this, errMessage);
                            }
                        });

                    }
                });
    }

    interface ProfitListCallback{
        void onGetPorfitList(ProfitListBean profitListBean);
    }
    /**
     * 财富收益列表
     */
    protected void requestProfitListData(String symbol, ProfitListCallback callback){
        CapitalApi.getFinanceProfitList(symbol, mActive, 1000, 1, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                ProfitListBean bean = (ProfitListBean)object;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onGetPorfitList(bean);
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(CapitalBaseActivity.this, errMessage);
                    }
                });

            }
        });
    }

}
