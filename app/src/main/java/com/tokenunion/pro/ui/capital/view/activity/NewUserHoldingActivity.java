package com.tokenunion.pro.ui.capital.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.ui.capital.model.NoviceCommonBean;
import com.tokenunion.pro.ui.capital.model.NoviceOrdersListBean;
import com.tokenunion.pro.utils.AppUtils;
import com.tokenunion.pro.utils.StringUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.LineDividerDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewUserHoldingActivity extends BaseActivity {
    private static final String TAG = NewUserHoldingActivity.class.getSimpleName();
    private static final int PAGE_SIZE = 15;

    private int mPageIndex = 1;
    private int mTotalNum = 0;

    @BindView(R.id.layout_common_actionbar_left)
    TextView layoutCommonActionbarLeft;
    @BindView(R.id.layout_common_actionbar_title)
    TextView layoutCommonActionbarTitle;
    @BindView(R.id.layout_common_actionbar_root)
    LinearLayout layoutCommonActionbarRoot;
    @BindView(R.id.activity_new_user_holding_quota_hold)
    TextView activityNewUserHoldingQuotaHold;
    @BindView(R.id.imageview_no_data)
    ImageView imageviewNoData;
    @BindView(R.id.activity_new_user_holding_list)
    RecyclerView activityNewUserHoldingList;
    @BindView(R.id.activity_new_user_holding_refresh)
    SmartRefreshLayout activityNewUserHoldingRefresh;
    @BindView(R.id.activity_new_user_holding_data_container)
    View activityNewUserHoldingDataContainer;
    @BindView(R.id.activity_new_user_holding_tag_welcome)
    TextView activityNewUserHoldingTagWelcome;
    @BindView(R.id.activity_new_user_holding_tag_fee)
    TextView activityNewUserHoldingTagFee;

    private List<NoviceOrdersListBean.NewUserOrdersBean> mListData = new ArrayList<>();
    private EasyRVAdapter<NoviceOrdersListBean.NewUserOrdersBean> mAdapter;

    // 顶部的tags TextView
    private List<TextView> mListTextviewTags = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_holding);
        ButterKnife.bind(this);

        activityNewUserHoldingList.setLayoutManager(new LinearLayoutManager(this));//, LinearLayoutManager.VERTICAL, false));
        activityNewUserHoldingList.setHasFixedSize(true);
        activityNewUserHoldingList.setNestedScrollingEnabled(false);

        initData();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListData.clear();
        mPageIndex = 1;
        requestData();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(getString(R.string.profit_new_user_holdings_title));
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        mListTextviewTags.add(activityNewUserHoldingTagWelcome);
        mListTextviewTags.add(activityNewUserHoldingTagFee);

        activityNewUserHoldingList.setLayoutManager(new LinearLayoutManager(this));
        LineDividerDecoration decoration = new LineDividerDecoration(this);
        int margin = DensityUtils.dip2px(this, 22);
        decoration.setLeftOffset(margin);
        decoration.setRightOffset(margin);
        activityNewUserHoldingList.addItemDecoration(decoration);

        activityNewUserHoldingRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mListData.clear();
                mPageIndex = 1;
                requestData();
                activityNewUserHoldingRefresh.finishRefresh();
            }
        });

        activityNewUserHoldingRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int pages = 0;
                if (mTotalNum > 0 && mTotalNum % PAGE_SIZE == 0) {
                    pages = mTotalNum / PAGE_SIZE;
                } else {
                    pages = mTotalNum / PAGE_SIZE + 1;
                }
                if (mPageIndex < pages) {
                    mPageIndex++;
                    requestData();
                }
                activityNewUserHoldingRefresh.finishLoadMore();
            }
        });
    }

    public void onBackClicked(View view) {
        finish();
    }

    public void onLockCapitalClicked(View view) {
        if (verifyTradePasswordAndSet()) {
            jumpToActivity(NewUserPurchaseActivity.class);
        }
    }

    private void updataListData(NoviceOrdersListBean bean) {
        if (null == mListData || mListData.isEmpty()) {
            mListData = bean.getOrders();

            mAdapter = new EasyRVAdapter<NoviceOrdersListBean.NewUserOrdersBean>(
                    NewUserHoldingActivity.this, mListData, R.layout.item_novice_orderlist_layout) {
                @Override
                public void onBindViewHolder(EasyRVHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                }

                @Override
                protected void onBindData(EasyRVHolder viewHolder, int position, NoviceOrdersListBean.NewUserOrdersBean item) {
                    viewHolder.setText(R.id.item_novice_orderlist_layout_time, item.getOrderTime());
                    viewHolder.setText(R.id.item_novice_orderlist_layout_amount, item.getAmount() + " " + item.getSymbol());
                    // viewHolder.setText(R.id.item_novice_orderlist_layout_period, item.getPeriodDays() + "/" + item.getDeadline());

                    TextView textView = viewHolder.getView(R.id.item_novice_orderlist_layout_period);
                    String strData = item.getPeriodDays() + " / " + item.getDeadline();
                    textView.setText(strData);
                    AppUtils.changeTextViewTextColor(textView, strData,
                            strData.indexOf(item.getPeriodDays()),
                            strData.indexOf(item.getPeriodDays()) + item.getPeriodDays().length());
                    viewHolder.setText(R.id.item_novice_orderlist_layout_income, "--");
                }
            };
            activityNewUserHoldingList.setAdapter(mAdapter);
        } else {
            mListData.addAll(bean.getOrders());
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 显示顶部的tags，比如"Welcome、0 Fee"
     *
     * @param tagDes
     */
    private void showTags(String tagDes) {
        if (StringUtils.isEmpty(tagDes)) {
            return;
        }
        String[] tags = tagDes.split(",");

        for (int i = 0; i < tags.length; i++) {
            if (i >= mListTextviewTags.size()) {
                break;
            }
            mListTextviewTags.get(i).setVisibility(View.VISIBLE);
            mListTextviewTags.get(i).setText(tags[i]);
        }
    }

    private void requestData() {
        CapitalApi.getNoviceOrdersList("", PAGE_SIZE, mPageIndex,
                mActive, new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        NoviceOrdersListBean bean = (NoviceOrdersListBean) object;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (null != bean && null != bean.getOrders() && !bean.getOrders().isEmpty()) {
                                    mTotalNum = bean.getTotalNum();

                                    // 显示列表
                                    updataListData(bean);

                                    imageviewNoData.setVisibility(View.GONE);
                                    activityNewUserHoldingDataContainer.setVisibility(View.VISIBLE);
                                } else {
                                    activityNewUserHoldingDataContainer.setVisibility(View.GONE);
                                    imageviewNoData.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(NewUserHoldingActivity.this, errMessage);
                                activityNewUserHoldingDataContainer.setVisibility(View.GONE);
                                imageviewNoData.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });

        CapitalApi.getNoviceCommon(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                List<NoviceCommonBean> productList = (List<NoviceCommonBean>) object;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null != productList && !productList.isEmpty()) {
                            activityNewUserHoldingQuotaHold.setText(productList.get(0).getBonusString());

                            // 显示顶部的tags
                            showTags(productList.get(0).getDes());
                        } else {
                            activityNewUserHoldingQuotaHold.setText("");
                        }
                    }
                });

            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(NewUserHoldingActivity.this, errMessage);
                    }
                });
            }
        });

    }
}
