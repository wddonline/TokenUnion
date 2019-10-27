package com.tokenunion.pro.ui.capital.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.ui.capital.adapter.TUCRecordAdapter;
import com.tokenunion.pro.ui.capital.model.FinanceListBean;
import com.tokenunion.pro.ui.capital.model.TimeOrderListBean;
import com.tokenunion.pro.ui.capital.view.fragment.CapitalHomeFragment;
import com.tokenunion.pro.ui.wallet.view.activity.BillRecordActivity;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.capital.model.ProfitListBean;
import com.tokenunion.pro.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;

public class TUCRecordActivity extends CapitalBaseActivity {

    private FinanceListBean mProduct;
    private RecyclerView mListView;
    private TUCRecordAdapter mAdapter;
    private ImageView mImageViewNoData;
    private SmartRefreshLayout mRefreshLayout;

    /**
     * 持有资产列表
     */
    private TimeOrderListBean mHoldRecords;

    /**
     * 收益列表数据
     */
    private ProfitListBean mProfitListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuc_record);
        initData();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        requestOverviewData(mProduct.getSymbol());
        requestTimeOrderListData(mProduct.getSymbol());
        requestProfitListData(mProduct.getSymbol());
    }

    @Override
    protected void initData() {
        mProduct = getIntent().getParcelableExtra(CapitalHomeFragment.BUNDLE_KEY_PRODUCT);
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        if(null != mProduct) {
            titleView.setText(mProduct.getSymbol());
        }
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        mRefreshLayout = findViewById(R.id.activity_tuc_record_refresh);
        mRefreshLayout.setRefreshFooter(new FalsifyFooter(this));
        mListView = findViewById(R.id.activity_tuc_record_list);
        mListView.setLayoutManager(new LinearLayoutManager(this));

        mImageViewNoData = findViewById(R.id.imageview_no_data);
    }

    @Override
    protected void updateData() {
        // 持有记录 mHoldRecords
        if(null != mHoldRecords && null != mProfitListBean) {
            if (mHoldRecords.getTotalNum()> 0 ||
                    mProfitListBean.getTotalNum() > 0) {
                // 数据都不为空，则显示
                mImageViewNoData.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mAdapter = new TUCRecordAdapter(this, mHoldRecords, mProfitListBean);
                mAdapter.setOnItemClickedListener(new TUCRecordAdapter.OnItemClickedListener() {
                    @Override
                    public void onBillRecordClicked() {
                        jumpToActivity(BillRecordActivity.class);
                    }
                });
                mListView.setAdapter(mAdapter);
            } else {
                // 否则，显示没有数据的图
                mImageViewNoData.setVisibility(View.VISIBLE);
                mRefreshLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void updateOverviewData() {
        requestAssetInfo(mProduct.getSymbol(), mAssetOverviewBean.getProdId());
        setTopCardViews();
    }

    public void onBackClicked(View view) {
        finish();
    }

    private void setTopCardViews() {
        TextView quotaTitleView = findViewById(R.id.activity_tuc_record_quota_title);
        String format = getString(R.string.quota_hold_format);
        quotaTitleView.setText(String.format(format, mAssetOverviewBean.getSymbol()));
        TextView quotaView = findViewById(R.id.activity_tuc_record_quota_hold);
        quotaView.setText(mAssetOverviewBean.getAmount()); // 持有额度，数量
        TextView increaseView = findViewById(R.id.activity_tuc_record_interest_added);
        increaseView.setText(mAssetOverviewBean.getSumProfit()); // 已获收益
    }

    public void onLockCapitalClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CapitalHomeFragment.BUNDLE_KEY_PRODUCT, mProduct);
        if(verifyTradePasswordAndSet()) {
            jumpToActivity(TUCLockCapitalActivity.class, bundle);
        }
    }

    /**
     * 查询持有资产列表信息----定期理财持有资产
     */
    protected void requestTimeOrderListData(String symbol){
        CapitalApi.getFinanceTimeOrderList(symbol, mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mHoldRecords = (TimeOrderListBean)object;
                runOnUiThread(() -> updateData());
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(() -> ToastUtils.showToast(TUCRecordActivity.this, errMessage));
            }
        });
    }

    /**
     * 财富收益列表--活期
     */
    protected void requestProfitListData(String symbol){
        CapitalApi.getFinanceProfitList(symbol, mActive, 1000, 1, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mProfitListBean = (ProfitListBean)object;

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
                        ToastUtils.showToast(TUCRecordActivity.this, errMessage);
                    }
                });

            }
        });
    }
}
