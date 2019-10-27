package com.tokenunion.pro.ui.capital.view.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tokenunion.pro.ui.capital.model.LeaderParticularsBean;
import com.tokenunion.pro.ui.capital.model.ProfitParticularsListBean;
import com.anypocket.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.manager.model.User;
import com.tokenunion.pro.ui.capital.CapitalApi;
import com.tokenunion.pro.ui.capital.model.FinanceInfoBean;
import com.tokenunion.pro.utils.BusinessUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.List;

public class InterestRuleActivity extends CapitalBaseActivity {

    private SmartRefreshLayout mRefreshLayout;
    private List<LeaderParticularsBean> mLeaderRewards;
    private ProfitParticularsListBean mProfitParticularsListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_rule);
        initData();
        initViews();

        requestTotalFinanceData();
        requestListData();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.interest_rule);
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        mRefreshLayout = findViewById(R.id.activity_interest_rule_refresh);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                requestTotalFinanceData();
//                requestListData(); // 刷新直接再次请求有问题，导致列表中数据重复累加。如果需要下拉刷新，需要处理一下初始数据
                mRefreshLayout.finishRefresh();
            }
        });
        mRefreshLayout.setRefreshFooter(new FalsifyFooter(this));
    }

    @Override
    protected void updateData() {

    }

    @Override
    protected void updateOverviewData() {

    }

    private void setInterestData() {
        TextView updateView = findViewById(R.id.activity_interest_rule_update);
        String format = getString(R.string.update_time_format);
        updateView.setText(mProfitParticularsListBean.getUpdateTime());
                //String.format(format, DatetimeUtils.timeStamp2Date(System.currentTimeMillis(), "yyyy-MM-dd")));


        TableLayout tableLayout = findViewById(R.id.activity_interest_rule_detail);
        final int BG_COLOR = Color.parseColor("#EDEDED");
        final int HEIGHT = DensityUtils.dip2px(this, 1);
        ProfitParticularsListBean.ProfitDetailsBean detail;
        TableRow tableRow;
        View dividerView;
        TextView itemView;
        int totalCount = mProfitParticularsListBean.getProfitDetails().size();
        for (int i = 0; i < totalCount; i++) {
            detail = mProfitParticularsListBean.getProfitDetails().get(i);
            tableRow = (TableRow) getLayoutInflater().inflate(R.layout.item_interest_rule, null);

            itemView = (TextView) ((ViewGroup)tableRow.getChildAt(0)).getChildAt(0);
            if(i != (totalCount-1)) {
                itemView.setText(
                        String.format(getString(R.string.interest_rule_include_format),
                        detail.getFloorBal(), detail.getCapBal()));
            }else{
                itemView.setText(
                        String.format(getString(R.string.interest_rule_up_format),
                        detail.getFloorBal()));
            }
            boolean isOutstanding = false;

            itemView.setSelected(isOutstanding);
            itemView = (TextView) tableRow.getChildAt(1);
            itemView.setText(detail.getProfitRate()+ " %");
            itemView.setSelected(isOutstanding);

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            dividerView = new View(this);
            dividerView.setBackgroundColor(BG_COLOR);
            tableLayout.addView(dividerView, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, HEIGHT));
        }
    }

    private void setLeaderRewardData() {
        TableLayout tableLayout = findViewById(R.id.activity_interest_rule_reward);
        final int BG_COLOR = Color.parseColor("#EDEDED");
        final int HEIGHT = DensityUtils.dip2px(this, 1);
        LeaderParticularsBean reward;
        TableRow tableRow;
        View dividerView;
        TextView itemView;
        for (int i = 0; i < mLeaderRewards.size(); i++) {
            reward = mLeaderRewards.get(i);
            tableRow = (TableRow) getLayoutInflater().inflate(R.layout.item_interest_rule, null);

            itemView = (TextView) ((ViewGroup)tableRow.getChildAt(0)).getChildAt(0);
            String levelName;
            Drawable drawable;
            int color;
            int level = BusinessUtils.getLevelId(reward.getLevelCode());
            switch (level) {
                case User.LEVEL_YOUNG:
                    levelName = getString(R.string.level_star);
                    drawable = getResources().getDrawable(R.mipmap.star_black);
                    color = Color.parseColor("#DADADA");
                    break;
                case User.LEVEL_PREFERRED:
                    levelName = getString(R.string.level_jade);
                    drawable = getResources().getDrawable(R.mipmap.star_black);
                    color = Color.parseColor("#DADADA");
                    break;
                case User.LEVEL_ELITE:
                    levelName = getString(R.string.level_gold);
                    drawable = getResources().getDrawable(R.mipmap.star_red);
                    color = Color.parseColor("#A40C09");
                    break;
                case User.LEVEL_RESERVE:
                    levelName = getString(R.string.level_titanium);
                    drawable = getResources().getDrawable(R.mipmap.star_red);
                    color = Color.parseColor("#A40C09");
                    break;
                case User.LEVEL_WORLD:
                    levelName = getString(R.string.level_platinum);
                    drawable = getResources().getDrawable(R.mipmap.star_gold);
                    color = Color.parseColor("#B59962");
                    break;
                default:
                    levelName = getString(R.string.level_diamond);
                    drawable = getResources().getDrawable(R.mipmap.star_gold);
                    color = Color.parseColor("#B59962");
                    break;
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            itemView.setCompoundDrawables(drawable, null, null, null);
            itemView.setText(levelName + " "+ reward.getLevelNum() + "+");
            itemView.setTextColor(color);
            itemView = (TextView) tableRow.getChildAt(1);
            itemView.setText("$"+ reward.getRewardBal());

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            dividerView = new View(this);
            dividerView.setBackgroundColor(BG_COLOR);
            tableLayout.addView(dividerView, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, HEIGHT));
        }
    }

    public void onBackClicked(View view) {
        finish();
    }

    private void requestListData(){
        CapitalApi.getFinanceProfitParticularsList(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mProfitParticularsListBean = (ProfitParticularsListBean)object;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setInterestData();
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(InterestRuleActivity.this, errMessage);
                    }
                });
            }
        });


        CapitalApi.getFinanceLeaderParticulars(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mLeaderRewards = (List<LeaderParticularsBean>)object;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setLeaderRewardData();
                    }
                });
            }

            @Override
            public void onFailed(String errCode, String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(InterestRuleActivity.this, errMessage);
                    }
                });
            }
        });
    }

    /**
     * 更新财富信息显示
     * @param bean
     */
    private void refreshFinanceInfo(FinanceInfoBean bean){

        // 总资产------------
        // 财富总览
        TextView totalView = findViewById(R.id.activity_interest_rule_capital_total);
        totalView.setText(bean.getTotalBal());
        // 综合收益率
        TextView complexRateView = findViewById(R.id.activity_interest_rule_complex_rate);
        complexRateView.setText(bean.getProfitRate());
    }

    private void requestTotalFinanceData() {
        CapitalApi.getFinanceInfo("", mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                final FinanceInfoBean bean = (FinanceInfoBean) object;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshFinanceInfo(bean);
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(InterestRuleActivity.this, errMessage);
                    }
                });
            }
        });
    }

}
