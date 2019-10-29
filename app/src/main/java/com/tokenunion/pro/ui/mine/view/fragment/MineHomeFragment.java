package com.tokenunion.pro.ui.mine.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tokenunion.pro.ui.login.view.LoginActivity;
import com.tokenunion.pro.ui.mine.MineApi;
import com.tokenunion.pro.ui.mine.model.MemberUserInfo;
import com.tokenunion.pro.ui.mine.view.activity.AboutUsActivity;
import com.tokenunion.pro.ui.mine.view.activity.CustomerServiceActivity;
import com.tokenunion.pro.ui.mine.view.activity.MembershipCenterActivity;
import com.tokenunion.pro.ui.mine.view.activity.MessageActivity;
import com.tokenunion.pro.ui.mine.view.activity.SecurityCenterActivity;
import com.tokenunion.pro.ui.mine.view.activity.SystemSettingsActivity;
import com.tokenunion.pro.R;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.manager.model.User;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.invite.view.activity.InviteRecordActivity;
import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.NormalDlg;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.tokenunion.pro.utils.AppUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MineHomeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = MineHomeFragment.class.getSimpleName();
    private MemberUserInfo mMemberUserInfo;

    @Override
    protected int getRootLayoutId() {
        return R.layout.fragment_mine_home;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        EventBusUtils.register(this);
    }
    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
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
        mRootView.findViewById(R.id.fragment_mine_home_logout).setOnClickListener(this);

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

    private void updateTopData(){
        TextView nameView = mRootView.findViewById(R.id.fragment_mine_home_username);
        nameView.setText(mMemberUserInfo.getUserName());//("你大爷还是你大爷");
        TextView inviteView = mRootView.findViewById(R.id.fragment_mine_home_invite_code);
        String format = getString(R.string.invite_code_format);
        inviteView.setText(String.format(format, mMemberUserInfo.getInviteCode()));
        TextView teamSizeView = mRootView.findViewById(R.id.fragment_mine_home_team_size);
        teamSizeView.setText(mMemberUserInfo.getTeamBal());//("11.5/15"); // 团队规模
        TextView levelProgressView = mRootView.findViewById(R.id.fragment_mine_home_level_progress);
        levelProgressView.setText(mMemberUserInfo.getFriends());//("8/10"); // 星级好友
        TextView distributionView = mRootView.findViewById(R.id.fragment_mine_home_star_distribution);
        distributionView.setText(mMemberUserInfo.getDistribution());//("3/2"); // 星级分布

        int level = 0;
        try {
            level = Integer.parseInt(mMemberUserInfo.getLevelCode()
                    .substring(mMemberUserInfo.getLevelCode().length() - 1));
        }catch (Exception ex){
            ex.printStackTrace();
        }

        ///////
        ImageView topBgView = mRootView.findViewById(R.id.fragment_mine_home_heading);
        TextView userLevelView = mRootView.findViewById(R.id.fragment_mine_home_user_level);
        TextView nextLevelNameView = mRootView.findViewById(R.id.fragment_mine_home_next_level_name);
        TextView nextLevelHintView = mRootView.findViewById(R.id.fragment_mine_home_next_level_hint);

        final String LEVEL_HINT_FORMAT = getString(R.string.next_level_hint_format);
        String nextLevel;
        int ringId;
        int bgId;
        int color;
        int hintColor;
        switch (level) {
            case User.LEVEL_YOUNG:
                topBgView.setImageResource(R.mipmap.black_card_small);
                userLevelView.setText(R.string.level_star);
                nextLevel = getString(R.string.level_jade);
//                nextLevelNameView.setText(nextLevel);
                nextLevelNameView.setText(R.string.level_star);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
//                nextLevelHintView.setBackgroundResource(R.drawable.bg_mine_level_jade);

                ringId = R.drawable.level_ring;
                bgId = R.drawable.bg_level_rect_2;
                color = Color.parseColor("#DE524F");
                hintColor = Color.parseColor("#F0CDA5");
                break;
            case User.LEVEL_PREFERRED:
                topBgView.setImageResource(R.mipmap.black_card_small);
                userLevelView.setText(R.string.level_jade);
                nextLevel = getString(R.string.level_gold);
                nextLevelNameView.setText(R.string.level_jade);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
//                nextLevelHintView.setBackgroundResource(R.drawable.bg_mine_level_gold);

                ringId = R.drawable.level_ring;
                bgId = R.drawable.bg_level_rect_1;
                color = Color.parseColor("#DE524F");
                hintColor = Color.parseColor("#FFFFFF");
                break;
            case User.LEVEL_ELITE:
                topBgView.setImageResource(R.mipmap.red_card_small);
                userLevelView.setText(R.string.level_gold);
                nextLevel = getString(R.string.level_titanium);
                nextLevelNameView.setText(R.string.level_gold);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
//                nextLevelHintView.setBackgroundResource(R.drawable.bg_mine_level_titanium);

                ringId = R.drawable.level_ring;
                bgId = R.drawable.bg_level_rect_1;
                color = Color.parseColor("#DE524F");
                hintColor = Color.parseColor("#F0CDA5");
                break;
            case User.LEVEL_RESERVE:
                topBgView.setImageResource(R.mipmap.red_card_small);
                userLevelView.setText(R.string.level_titanium);
                nextLevel = getString(R.string.level_platinum);
                nextLevelNameView.setText(R.string.level_titanium);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
//                nextLevelHintView.setBackgroundResource(R.drawable.bg_mine_level_platinum);

                ringId = R.drawable.level_ring;
                bgId = R.drawable.bg_level_rect_3;
                color = Color.parseColor("#DE524F");
                hintColor = Color.parseColor("#E1E1E1");
                break;
            case User.LEVEL_WORLD:
                topBgView.setImageResource(R.mipmap.gold_card_small);
                userLevelView.setText(R.string.level_platinum);
                nextLevel = getString(R.string.level_diamond);
                nextLevelNameView.setText(R.string.level_platinum);
                nextLevelHintView.setText(String.format(LEVEL_HINT_FORMAT, nextLevel));
//                nextLevelHintView.setBackgroundResource(R.drawable.bg_mine_level_diamond);

                ringId = R.drawable.level_ring;
                bgId = R.drawable.bg_level_rect_3;
                color = Color.parseColor("#DDDDDD");
                hintColor = Color.parseColor("#CAAC89");
                break;
            default:
                topBgView.setImageResource(R.mipmap.gold_card_small);
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
        ringViews[0] = mRootView.findViewById(R.id.fragment_mine_home_level_ring_0);
        ringViews[1] = mRootView.findViewById(R.id.fragment_mine_home_level_ring_1);
        ringViews[2] = mRootView.findViewById(R.id.fragment_mine_home_level_ring_2);
//        ringViews[0].setImageResource(ringId);
//        ringViews[1].setImageResource(ringId);
//        ringViews[2].setImageResource(ringId);
//        userLevelView.setBackgroundResource(bgId);
//        nextLevelHintView.setTextColor(hintColor);
        ViewGroup rootView = mRootView.findViewById(R.id.fragment_mine_home_top_panel);
//        setTextColor(rootView, color);

        // 斜体
        AppUtils.setItalicFont(userLevelView);
    }

    @Override
    protected void lazyLoad() {
        doRequest();
    }

    /**
     * 获取用户信息
     */
    private void doRequest(){
        MineApi.getMemUserInfo(mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                mMemberUserInfo = (MemberUserInfo)object;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateTopData();
                    }
                });
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast(getContext(), errMessage);
                    }
                });
            }
        });
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

            case R.id.fragment_mine_home_logout:
                doLogout();
                break;

            default:
                break;

        }
    }

    private void doLogout() {
        final NormalDlg dlg = new NormalDlg(getActivity());
        dlg.setTitle(getString(R.string.mine_safe_exit_title));
        dlg.setMessage("");
        dlg.setYesClickListener(getString(R.string.mine_safe_exit_ok), new NormalDlg.OnYesClickListener() {
            @Override
            public void onYesClick() {
                dlg.dismiss();
                UserAccount.getInstance().setUserBean(null);
                getActivity().finish();

                jumpToActivity(LoginActivity.class);
            }
        });

        dlg.setNoClickListener(getString(R.string.mine_safe_exit_cancel), new NormalDlg.OnNoClickListener() {
            @Override
            public void onNoClick() {
                dlg.dismiss();
            }
        });

        dlg.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBusEvent(EventBusUtils.CommonEvent event) {
        switch (event.getId()){
            case EventBusUtils.EVENT_HAS_NEW_MESSAGE:
                // 有新消息
                if(null != mRootView) {
                    mRootView.findViewById(R.id.fragment_mine_home_message_new).setVisibility(View.VISIBLE);
                }
                break;

            case EventBusUtils.EVENT_NO_NEW_MESSAGE:
                // 无新消息
                if(null != mRootView) {
                    mRootView.findViewById(R.id.fragment_mine_home_message_new).setVisibility(View.GONE);
                }
                break;

            default:
                break;
        }
    }

}
