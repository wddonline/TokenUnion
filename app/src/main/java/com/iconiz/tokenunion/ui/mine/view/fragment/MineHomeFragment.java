package com.iconiz.tokenunion.ui.mine.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.manager.UserManager;
import com.iconiz.tokenunion.manager.model.User;
import com.iconiz.tokenunion.ui.base.BaseFragment;
import com.iconiz.tokenunion.ui.invite.view.activity.InviteRecordActivity;
import com.iconiz.tokenunion.ui.mine.view.activity.AboutUsActivity;
import com.iconiz.tokenunion.ui.mine.view.activity.CustomerServiceActivity;
import com.iconiz.tokenunion.ui.mine.view.activity.MembershipCenterActivity;
import com.iconiz.tokenunion.ui.mine.view.activity.MessageActivity;
import com.iconiz.tokenunion.ui.mine.view.activity.SecurityCenterActivity;
import com.iconiz.tokenunion.ui.mine.view.activity.SystemSettingsActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;

public class MineHomeFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_mine_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        SmartRefreshLayout refreshLayout = mRootView.findViewById(R.id.fragment_mine_home_refresh);
        FalsifyHeader header = new FalsifyHeader(getContext());
        header.setBackgroundColor(Color.parseColor("#2E3039"));
        refreshLayout.setRefreshHeader(header);
        header.getLayoutParams().height = 100000;
        refreshLayout.setRefreshFooter(new FalsifyFooter(getContext()));

        mRootView.findViewById(R.id.fragment_mine_home_invite).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_mine_home_message).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_mine_home_membership).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_mine_home_settings).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_mine_home_customer_service).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_mine_home_about_us).setOnClickListener(this);
        mRootView.findViewById(R.id.fragment_mine_home_safety).setOnClickListener(this);

        TextView nameView = mRootView.findViewById(R.id.fragment_mine_home_username);
        nameView.setText("你大爷还是你大爷");
        TextView inviteView = mRootView.findViewById(R.id.fragment_mine_home_invite_code);
        String format = getString(R.string.invite_code_format);
        inviteView.setText(String.format(format, "A011E3TR"));
        TextView teamSizeView = mRootView.findViewById(R.id.fragment_mine_home_team_size);
        teamSizeView.setText("11.5/15");
        TextView levelProgressView = mRootView.findViewById(R.id.fragment_mine_home_level_progress);
        levelProgressView.setText("8/10");
        TextView distributionView = mRootView.findViewById(R.id.fragment_mine_home_star_distribution);
        distributionView.setText("3/2");

        TextView userLevelView = mRootView.findViewById(R.id.fragment_mine_home_user_level);
        TextView nextLevelNameView = mRootView.findViewById(R.id.fragment_mine_home_next_level_name);
        TextView nextLevelHintView = mRootView.findViewById(R.id.fragment_mine_home_next_level_hint);

        final String LEVEL_HINT_FORMAT = getString(R.string.next_level_hint_format);
        String nextLevel;
        int card;
        switch (UserManager.getInstance().getUser().getUserLevel()) {
            case User.LEVEL_YOUNG:
                card = R.mipmap.black_card_small;
                userLevelView.setText(R.string.level_star);
                nextLevel = getString(R.string.level_jade);
                nextLevelNameView.setText(nextLevel);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
                break;
            case User.LEVEL_PREFERRED:
                card = R.mipmap.black_card_small;
                userLevelView.setText(R.string.level_jade);
                nextLevel = getString(R.string.level_gold);
                nextLevelNameView.setText(nextLevel);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
                break;
            case User.LEVEL_ELITE:
                card = R.mipmap.red_card_small;
                userLevelView.setText(R.string.level_gold);
                nextLevel = getString(R.string.level_titanium);
                nextLevelNameView.setText(nextLevel);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
                break;
            case User.LEVEL_RESERVE:
                card = R.mipmap.red_card_small;
                userLevelView.setText(R.string.level_titanium);
                nextLevel = getString(R.string.level_platinum);
                nextLevelNameView.setText(nextLevel);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
                break;
            case User.LEVEL_WORLD:
                card = R.mipmap.gold_card_small;
                userLevelView.setText(R.string.level_platinum);
                nextLevel = getString(R.string.level_diamond);
                nextLevelNameView.setText(nextLevel);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
                break;
            default:
                card = R.mipmap.gold_card_small;
                userLevelView.setText(R.string.level_diamond);
                nextLevel = getString(R.string.level_diamond);
                nextLevelNameView.setText(nextLevel);
                nextLevelHintView.setText(R.string.diamond_level_arrived);
                break;
        }

        ImageView cardView = mRootView.findViewById(R.id.fragment_mine_home_card);
        cardView.setImageResource(card);

        ImageView[] ringViews = new ImageView[3];
        ringViews[0] = mRootView.findViewById(R.id.fragment_mine_home_level_ring_0);
        ringViews[1] = mRootView.findViewById(R.id.fragment_mine_home_level_ring_1);
        ringViews[2] = mRootView.findViewById(R.id.fragment_mine_home_level_ring_2);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_mine_home_invite:
                jumpToActivity(InviteRecordActivity.class);
                break;
            case R.id.fragment_mine_home_message:
                jumpToActivity(MessageActivity.class);
                break;
            case R.id.fragment_mine_home_membership:
                jumpToActivity(MembershipCenterActivity.class);
                break;
            case R.id.fragment_mine_home_settings:
                jumpToActivity(SystemSettingsActivity.class);
                break;
            case R.id.fragment_mine_home_customer_service:
                jumpToActivity(CustomerServiceActivity.class);
                break;
            case R.id.fragment_mine_home_safety:
                jumpToActivity(SecurityCenterActivity.class);
                break;
            case R.id.fragment_mine_home_about_us:
                jumpToActivity(AboutUsActivity.class);
                break;

        }
    }
}
