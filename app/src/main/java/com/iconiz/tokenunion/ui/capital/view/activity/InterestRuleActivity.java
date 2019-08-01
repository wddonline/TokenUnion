package com.iconiz.tokenunion.ui.capital.view.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.manager.model.User;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.capital.model.InterestDetail;
import com.iconiz.tokenunion.ui.capital.model.LeaderReward;
import com.iconiz.tokenunion.utils.DatetimeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.yidaichu.android.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class InterestRuleActivity extends BaseActivity {

    private SmartRefreshLayout mRefreshLayout;
    private List<InterestDetail> mInterestDetails;
    private List<LeaderReward> mLeaderRewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_rule);
        initData();
        initViews();
    }

    @Override
    protected void initData() {
        mInterestDetails = new ArrayList<>();
        InterestDetail detail = new InterestDetail();
        detail.setBalance("0 ~ 300（含）");
        detail.setMonthRate("6.0% ~ 8.0%");
        detail.setOutstanding(false);
        mInterestDetails.add(detail);

        detail = new InterestDetail();
        detail.setBalance("300 ~ 500（含）");
        detail.setMonthRate("8.0% ~ 10.0%");
        detail.setOutstanding(false);
        mInterestDetails.add(detail);

        detail = new InterestDetail();
        detail.setBalance("500 ~ 3000（含）");
        detail.setMonthRate("10.0% ~ 12.0%");
        detail.setOutstanding(true);
        mInterestDetails.add(detail);

        detail = new InterestDetail();
        detail.setBalance("3000 ~ 12000（含）");
        detail.setMonthRate("12.0% ~ 14.0%");
        detail.setOutstanding(false);
        mInterestDetails.add(detail);

        detail = new InterestDetail();
        detail.setBalance("12000 ~ 30000（含）");
        detail.setMonthRate("14.0% ~ 16.0%");
        detail.setOutstanding(false);
        mInterestDetails.add(detail);

        detail = new InterestDetail();
        detail.setBalance("30000 以上");
        detail.setMonthRate("16.0% ~ 18.0%");
        detail.setOutstanding(false);
        mInterestDetails.add(detail);

        mLeaderRewards = new ArrayList<>();
        LeaderReward reward = new LeaderReward();
        reward.setLevel(User.LEVEL_RESERVE);
        reward.setUserCount(3);
        reward.setRewardValue("$ 500");
        mLeaderRewards.add(reward);

        reward = new LeaderReward();
        reward.setLevel(User.LEVEL_WORLD);
        reward.setUserCount(3);
        reward.setRewardValue("$ 700");
        mLeaderRewards.add(reward);

        reward = new LeaderReward();
        reward.setLevel(User.LEVEL_INFINITE);
        reward.setUserCount(3);
        reward.setRewardValue("$ 1000");
        mLeaderRewards.add(reward);
    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.interest_rule);
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        mRefreshLayout = findViewById(R.id.activity_interest_rule_refresh);
        mRefreshLayout.setRefreshFooter(new FalsifyFooter(this));
        setTopCardViews();
        TextView updateView = findViewById(R.id.activity_interest_rule_update);
        String format = getString(R.string.update_time_format);
        updateView.setText(String.format(format, DatetimeUtils.timeStamp2Date(System.currentTimeMillis(), "yyyy-MM-dd")));

        setInterestData();
        setLeaderRewardData();
    }

    private void setTopCardViews() {
        TextView totalView = findViewById(R.id.activity_interest_rule_capital_total);
        totalView.setText("4589.5231");
        TextView complexRateView = findViewById(R.id.activity_interest_rule_complex_rate);
        complexRateView.setText("18.0");
    }

    private void setInterestData() {
        TableLayout tableLayout = findViewById(R.id.activity_interest_rule_detail);
        final int BG_COLOR = Color.parseColor("#EDEDED");
        final int HEIGHT = DensityUtils.dip2px(this, 1);
        InterestDetail detail;
        TableRow tableRow;
        View dividerView;
        TextView itemView;
        for (int i = 0; i < mInterestDetails.size(); i++) {
            detail = mInterestDetails.get(i);
            tableRow = (TableRow) getLayoutInflater().inflate(R.layout.item_interest_rule, null);

            itemView = (TextView) ((ViewGroup)tableRow.getChildAt(0)).getChildAt(0);
            itemView.setText(detail.getBalance());
            itemView.setSelected(detail.isOutstanding());
            itemView = (TextView) tableRow.getChildAt(1);
            itemView.setText(detail.getMonthRate());
            itemView.setSelected(detail.isOutstanding());

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
        LeaderReward reward;
        TableRow tableRow;
        View dividerView;
        TextView itemView;
        for (int i = 0; i < mLeaderRewards.size(); i++) {
            reward = mLeaderRewards.get(i);
            tableRow = (TableRow) getLayoutInflater().inflate(R.layout.item_interest_rule, null);

            itemView = (TextView) ((ViewGroup)tableRow.getChildAt(0)).getChildAt(0);
            String level;
            Drawable drawable;
            int color;
            switch (reward.getLevel()) {
                case User.LEVEL_YOUNG:
                    level = getString(R.string.level_star);
                    drawable = getResources().getDrawable(R.mipmap.star_black);
                    color = Color.parseColor("#DADADA");
                    break;
                case User.LEVEL_PREFERRED:
                    level = getString(R.string.level_jade);
                    drawable = getResources().getDrawable(R.mipmap.star_black);
                    color = Color.parseColor("#DADADA");
                    break;
                case User.LEVEL_ELITE:
                    level = getString(R.string.level_gold);
                    drawable = getResources().getDrawable(R.mipmap.star_red);
                    color = Color.parseColor("#A40C09");
                    break;
                case User.LEVEL_RESERVE:
                    level = getString(R.string.level_titanium);
                    drawable = getResources().getDrawable(R.mipmap.star_red);
                    color = Color.parseColor("#A40C09");
                    break;
                case User.LEVEL_WORLD:
                    level = getString(R.string.level_platinum);
                    drawable = getResources().getDrawable(R.mipmap.star_gold);
                    color = Color.parseColor("#B59962");
                    break;
                default:
                    level = getString(R.string.level_diamond);
                    drawable = getResources().getDrawable(R.mipmap.star_gold);
                    color = Color.parseColor("#B59962");
                    break;
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            itemView.setCompoundDrawables(drawable, null, null, null);
            itemView.setText(level + reward.getUserCount() + "+");
            itemView.setTextColor(color);
            itemView = (TextView) tableRow.getChildAt(1);
            itemView.setText(reward.getRewardValue());

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            dividerView = new View(this);
            dividerView.setBackgroundColor(BG_COLOR);
            tableLayout.addView(dividerView, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, HEIGHT));
        }
    }

    public void onBackClicked(View view) {
        finish();
    }

}
