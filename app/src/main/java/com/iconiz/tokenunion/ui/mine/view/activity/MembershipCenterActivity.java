package com.iconiz.tokenunion.ui.mine.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.manager.UserManager;
import com.iconiz.tokenunion.manager.model.User;
import com.iconiz.tokenunion.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MembershipCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_center);
        initData();
        initViews();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.customer_center);
        findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

        initUserCard();

        TextView bankHintView = findViewById(R.id.activity_membership_center_bank_hint);
        String binkHint = getString(R.string.membership_wealth_limit);
        String num;
        switch (UserManager.getInstance().getUser().getUserLevel()) {
            case User.LEVEL_YOUNG:
                num = "857";
                binkHint = String.format(binkHint, num, "0 ($)");
                break;
            case User.LEVEL_PREFERRED:
                num = "1,356";
                binkHint = String.format(binkHint, num, "$ 1,000");
                break;
            case User.LEVEL_ELITE:
                num = "2,356";
                binkHint = String.format(binkHint, num, "2,000 ($)");
                break;
            case User.LEVEL_RESERVE:
                num = "3,356";
                binkHint = String.format(binkHint, num, "3,000 ($)");
                break;
            case User.LEVEL_WORLD:
                num = "5,356";
                binkHint = String.format(binkHint, num, "5,000 ($)");
                break;
            default:
                num = "20,356";
                binkHint = String.format(binkHint, num, "10,000 ($)");
                break;
        }
        SpannableString span = new SpannableString(binkHint);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#F0CDA5"));
        int index = binkHint.indexOf(' ');
        span.setSpan(colorSpan, index, index + num.length() + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        bankHintView.setText(span);

        initRightIcons();

        TextView utcView = findViewById(R.id.activity_membership_center_utc_time);
        //（UTC Z HH:mm）
        SimpleDateFormat dateFormat = new SimpleDateFormat("Z hh:mm a", Locale.ENGLISH);
        String dateStr = dateFormat.format(new Date());
        utcView.setText(String.format(getString(R.string.system_time_format), dateStr));

        setVisaApplyViews();
    }

    private void initUserCard() {
        TextView nameView = findViewById(R.id.activity_membership_center_username);
        nameView.setText("你大爷还是你大爷");
        TextView inviteView = findViewById(R.id.activity_membership_center_invite_code);
        String format = getString(R.string.invite_code_format);
        inviteView.setText(String.format(format, "A011E3TR"));
        TextView teamSizeView = findViewById(R.id.activity_membership_center_team_size);
        teamSizeView.setText("11.5/15");
        TextView levelProgressView = findViewById(R.id.activity_membership_center_level_progress);
        levelProgressView.setText("8/10");
        TextView distributionView = findViewById(R.id.activity_membership_center_star_distribution);
        distributionView.setText("3/2");

        TextView userLevelView = findViewById(R.id.activity_membership_center_user_level);
        TextView nextLevelNameView = findViewById(R.id.activity_membership_center_next_level_name);
        TextView nextLevelHintView = findViewById(R.id.activity_membership_center_next_level_hint);

        final String LEVEL_HINT_FORMAT = getString(R.string.next_level_hint_format);
        int card;
        String nextLevel;
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
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
                break;
        }

        ImageView cardView = findViewById(R.id.activity_membership_center_card);
        cardView.setImageResource(card);
        ImageView[] ringViews = new ImageView[3];
        ringViews[0] = findViewById(R.id.activity_membership_center_level_ring_0);
        ringViews[1] = findViewById(R.id.activity_membership_center_level_ring_1);
        ringViews[2] = findViewById(R.id.activity_membership_center_level_ring_2);
    }

    private void initRightIcons() {
        String[] descs = getResources().getStringArray(R.array.membership_right_descs);
        int[] icons;
        String type;
        switch (UserManager.getInstance().getUser().getUserLevel()) {
            case User.LEVEL_YOUNG:
                icons = new int[3];
                type = "black";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                break;
            case User.LEVEL_PREFERRED:
                icons = new int[4];
                type = "black";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                descs[3] = String.format(descs[3], "6%");
                break;
            case User.LEVEL_ELITE:
                icons = new int[5];
                type = "red";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                descs[3] = String.format(descs[3], "9%");
                descs[4] = String.format(descs[4], "$7,000");
                break;
            case User.LEVEL_RESERVE:
                icons = new int[5];
                type = "red";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                descs[3] = String.format(descs[3], "12%");
                descs[4] = String.format(descs[4], "$15,000");
                break;
            case User.LEVEL_WORLD:
                icons = new int[6];
                type = "gold";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                descs[3] = String.format(descs[3], "15%");
                descs[4] = String.format(descs[4], "$100,000");
                descs[5] = descs[5];
                break;
            default:
                icons = new int[6];
                type = "gold";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = descs[2];
                descs[3] = String.format(descs[3], "15%+");
                descs[4] = String.format(descs[4], "$250,000");
                descs[5] = descs[5];
                break;
        }
        for (int i = 0; i < icons.length; i++) {
            icons[i] = getResources().getIdentifier("icon_member_right_" + type + "_" + i, "mipmap", getPackageName());
        }
        String[] rights = getResources().getStringArray(R.array.membership_rights);
        ViewGroup rightView;
        String id;
        for (int i = 0; i < 6; i++) {
            id = "activity_membership_center_right" + i;
            rightView = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
            if (i < icons.length) {
                rightView.setVisibility(View.VISIBLE);
                ((ImageView)rightView.getChildAt(0)).setImageResource(icons[i]);
                ((TextView)rightView.getChildAt(1)).setText(rights[i]);
                ((TextView)rightView.getChildAt(2)).setText(descs[i]);
            } else {
                rightView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setVisaApplyViews() {
        View visaPanel = findViewById(R.id.activity_membership_center_visa_panel);
        View platinumSignView = findViewById(R.id.activity_membership_center_platinum_visa_sign);
        switch (UserManager.getInstance().getUser().getUserLevel()) {
            case User.LEVEL_INFINITE:
                visaPanel.setVisibility(View.VISIBLE);
                visaPanel.setBackgroundResource(R.drawable.bg_diamond_visa_apply);
                platinumSignView.setVisibility(View.GONE);
                break;
            default:
                visaPanel.setVisibility(View.GONE);
                break;
        }
    }


    public void onBackClicked(View view) {
        finish();
    }

    public void onMembershipDescClicked(View view) {
        jumpToActivity(MembershipDescActivity.class);
    }

    public void onApplyVisaClicked(View view) {

    }
}
