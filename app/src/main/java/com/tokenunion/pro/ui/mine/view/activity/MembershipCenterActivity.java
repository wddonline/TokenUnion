package com.tokenunion.pro.ui.mine.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.mine.MineApi;
import com.tokenunion.pro.ui.mine.model.MemberUserInfo;
import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.manager.UserManager;
import com.tokenunion.pro.manager.model.User;
import com.tokenunion.pro.utils.BusinessUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.utils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MembershipCenterActivity extends BaseActivity {
    private MemberUserInfo mMemberUserInfo;
    protected View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_center);
        initData();
        initViews();
        doRequest();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        TextView titleView = findViewById(R.id.layout_common_actionbar_title);
        titleView.setText(R.string.customer_center);
        mRootView = findViewById(R.id.activity_membership_center_rootview);
		findViewById(R.id.layout_common_actionbar_divider).setVisibility(View.GONE);

//        BusinessUtils.displayUserHeadImage(mRootView.findViewById(R.id.activity_membership_center_headimg));

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
                descs[2] = String.format(descs[2], "10", "1.5%");
                break;
            case User.LEVEL_PREFERRED:
                icons = new int[4];
                type = "black";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = String.format(descs[2], "10", "1.5%");
                descs[3] = String.format(descs[3], "6%");
                break;
            case User.LEVEL_ELITE:
                icons = new int[5];
                type = "red";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = String.format(descs[2], "10", "1.5%");
                descs[3] = String.format(descs[3], "9%");
                descs[4] = String.format(descs[4], "$15,000");
                break;
            case User.LEVEL_RESERVE:
                icons = new int[5];
                type = "red";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = String.format(descs[2], "10", "1.5%");
                descs[3] = String.format(descs[3], "12%");
                descs[4] = String.format(descs[4], "$15,000");
                break;
            case User.LEVEL_WORLD:
                icons = new int[6];
                type = "gold";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = String.format(descs[2], "10", "1.5%");
                descs[3] = String.format(descs[3], "15%");
                descs[4] = String.format(descs[4], "$100,000");
                descs[5] = descs[5];
                break;
            default:
                icons = new int[6];
                type = "gold";
                descs[0] = String.format(descs[0], "100%");
                descs[1] = String.format(descs[1], "10", "10%");
                descs[2] = String.format(descs[2], "10", "1.5%");
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

    private void setTextColor(ViewGroup viewGroup, int color) {
        View childView;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            childView = viewGroup.getChildAt(i);
            if (childView instanceof ViewGroup) {
                setTextColor((ViewGroup) childView, color);
            } else {
                if (childView instanceof TextView) {
                    ((TextView)childView).setTextColor(color);
                }
            }
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

    private void updateTopData(){
        TextView nameView = mRootView.findViewById(R.id.activity_membership_center_username);
        nameView.setText(mMemberUserInfo.getUserName());//("你大爷还是你大爷");
        TextView inviteView = mRootView.findViewById(R.id.activity_membership_center_invite_code);
        String format = getString(R.string.invite_code_format);
        inviteView.setText(String.format(format, mMemberUserInfo.getInviteCode()));
        TextView teamSizeView = mRootView.findViewById(R.id.activity_membership_center_team_size);
        teamSizeView.setText(mMemberUserInfo.getTeamBal());//("11.5/15"); // 团队规模
        TextView levelProgressView = mRootView.findViewById(R.id.activity_membership_center_level_progress);
        levelProgressView.setText(mMemberUserInfo.getFriends());//("8/10"); // 星级好友
        TextView distributionView = mRootView.findViewById(R.id.activity_membership_center_star_distribution);
        distributionView.setText(mMemberUserInfo.getDistribution());//("3/2"); // 星级分布

        int level = 0;
        try {
            level = Integer.parseInt(mMemberUserInfo.getLevelCode()
                    .substring(mMemberUserInfo.getLevelCode().length() - 1));
        }catch (Exception ex){
            ex.printStackTrace();
        }

        ///////
        ImageView topBgView = mRootView.findViewById(R.id.activity_membership_center_top_img);
        TextView userLevelView = mRootView.findViewById(R.id.activity_membership_center_user_level);
        TextView nextLevelNameView = mRootView.findViewById(R.id.activity_membership_center_next_level_name);
        TextView nextLevelHintView = mRootView.findViewById(R.id.activity_membership_center_next_level_hint);

        final String LEVEL_HINT_FORMAT = getString(R.string.next_level_hint_format);
        String nextLevel;
        int ringId;
        int bgId;
        int color;
        int hintColor;
        switch (level) {
            case User.LEVEL_YOUNG:
//                topBgView.setImageResource(R.mipmap.bg_mine_star_star);
                userLevelView.setText(R.string.level_star);
                nextLevel = getString(R.string.level_jade);
                nextLevelNameView.setText(R.string.level_star);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
//                nextLevelHintView.setBackgroundResource(R.drawable.bg_mine_level_jade);

                ringId = R.drawable.level_ring_2;
                bgId = R.drawable.bg_level_rect_2;
                color = Color.parseColor("#FFFFFF");
                hintColor = Color.parseColor("#F0CDA5");
                break;
            case User.LEVEL_PREFERRED:
//                topBgView.setImageResource(R.mipmap.bg_mine_star_jade);
                userLevelView.setText(R.string.level_jade);
                nextLevel = getString(R.string.level_gold);
                nextLevelNameView.setText(R.string.level_jade);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
//                nextLevelHintView.setBackgroundResource(R.drawable.bg_mine_level_gold);

                ringId = R.drawable.level_ring_1;
                bgId = R.drawable.bg_level_rect_1;
                color = Color.parseColor("#F0CDA5");
                hintColor = Color.parseColor("#FFFFFF");
                break;
            case User.LEVEL_ELITE:
//                topBgView.setImageResource(R.mipmap.bg_mine_star_gold);
                userLevelView.setText(R.string.level_gold);
                nextLevel = getString(R.string.level_titanium);
                nextLevelNameView.setText(R.string.level_gold);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
//                nextLevelHintView.setBackgroundResource(R.drawable.bg_mine_level_titanium);

                ringId = R.drawable.level_ring_1;
                bgId = R.drawable.bg_level_rect_1;
                color = Color.parseColor("#F0CDA5");
                hintColor = Color.parseColor("#F0CDA5");
                break;
            case User.LEVEL_RESERVE:
//                topBgView.setImageResource(R.mipmap.bg_mine_star_titanium);
                userLevelView.setText(R.string.level_titanium);
                nextLevel = getString(R.string.level_platinum);
                nextLevelNameView.setText(R.string.level_titanium);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
//                nextLevelHintView.setBackgroundResource(R.drawable.bg_mine_level_platinum);

                ringId = R.drawable.level_ring_3;
                bgId = R.drawable.bg_level_rect_3;
                color = Color.parseColor("#DDDDDD");
                hintColor = Color.parseColor("#E1E1E1");
                break;
            case User.LEVEL_WORLD:
//                topBgView.setImageResource(R.mipmap.bg_mine_star_platinum);
                userLevelView.setText(R.string.level_platinum);
                nextLevel = getString(R.string.level_diamond);
                nextLevelNameView.setText(R.string.level_platinum);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
//                nextLevelHintView.setBackgroundResource(R.drawable.bg_mine_level_diamond);

                ringId = R.drawable.level_ring_3;
                bgId = R.drawable.bg_level_rect_3;
                color = Color.parseColor("#DDDDDD");
                hintColor = Color.parseColor("#CAAC89");
                break;
            default:
//                topBgView.setImageResource(R.mipmap.bg_mine_star_diamond);
                userLevelView.setText(R.string.level_diamond);
                nextLevel = getString(R.string.level_diamond);
                nextLevelNameView.setText(R.string.level_diamond);
                nextLevelHintView.setText(R.string.diamond_level_arrived);
//                nextLevelHintView.setBackgroundResource(R.drawable.bg_mine_level_diamond);

                ringId = R.drawable.level_ring_4;
                bgId = R.drawable.bg_level_rect_4;
                color = Color.parseColor("#DBB07F");
                hintColor = Color.parseColor("#CAAC89");
                break;
        }

        ImageView[] ringViews = new ImageView[3];
        ringViews[0] = mRootView.findViewById(R.id.activity_membership_center_level_ring_0);
        ringViews[1] = mRootView.findViewById(R.id.activity_membership_center_level_ring_1);
        ringViews[2] = mRootView.findViewById(R.id.activity_membership_center_level_ring_2);
//        ringViews[0].setImageResource(ringId);
//        ringViews[1].setImageResource(ringId);
//        ringViews[2].setImageResource(ringId);
//        userLevelView.setBackgroundResource(bgId);
//        nextLevelHintView.setTextColor(hintColor);
        ViewGroup rootView = mRootView.findViewById(R.id.activity_membership_center_top_panel);
//        setTextColor(rootView, color);

        // 斜体
        AppUtils.setItalicFont(userLevelView);
    }

    private void updateBottomData() {
        TextView bankHintView = findViewById(R.id.activity_membership_center_bank_hint);
        String binkHint = getString(R.string.membership_wealth_limit);
        String num;
        int level = BusinessUtils.getLevelId(UserAccount.getInstance().getUserBean().getLevelCode());
        switch (level) {
            case User.LEVEL_YOUNG:
                num = mMemberUserInfo.getBal(); // 个人资产
                binkHint = String.format(binkHint, num, "0 ($)").replace("/ 0", "");
                break;
            case User.LEVEL_PREFERRED:
                num = mMemberUserInfo.getBal();
                binkHint = String.format(binkHint, num, mMemberUserInfo.getHoldBal()+ " ($)");
                break;
            case User.LEVEL_ELITE:
                num = mMemberUserInfo.getBal();
                binkHint = String.format(binkHint, num, mMemberUserInfo.getHoldBal()+ " ($)");
                break;
            case User.LEVEL_RESERVE:
                num = mMemberUserInfo.getBal();
                binkHint = String.format(binkHint, num, mMemberUserInfo.getHoldBal()+ " ($)");
                break;
            case User.LEVEL_WORLD:
                num = mMemberUserInfo.getBal();
                binkHint = String.format(binkHint, num, mMemberUserInfo.getHoldBal()+ " ($)");
                break;
            default:
                num = mMemberUserInfo.getBal();
                binkHint = String.format(binkHint, num, mMemberUserInfo.getHoldBal()+ " ($)");
                break;
        }
        SpannableString span = new SpannableString(binkHint);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#F0CDA5"));
//        int index = binkHint.indexOf(' ');
        int index = binkHint.indexOf(num);
        span.setSpan(colorSpan, index, index + num.length() + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        bankHintView.setText(span);

        initRightIcons();

        TextView utcView = findViewById(R.id.activity_membership_center_utc_time);
        //（UTC Z HH:mm）
        SimpleDateFormat dateFormat = new SimpleDateFormat("Z hh:mm a", Locale.ENGLISH);
        String dateStr = dateFormat.format(new Date());
//        utcView.setText(String.format(getString(R.string.system_time_format), dateStr));
        utcView.setText(getString(R.string.system_time_format));
    }

    /**
     * 获取用户信息
     */
    private void doRequest(){
        MineApi.getMemUserInfo(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mMemberUserInfo = (MemberUserInfo)object;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateTopData();
                        updateBottomData();
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(MembershipCenterActivity.this, errMessage);
                    }
                });
            }
        });
    }
}
