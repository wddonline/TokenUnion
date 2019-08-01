package com.iconiz.tokenunion.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.iconiz.tokenunion.R;
import com.iconiz.tokenunion.ui.base.BaseActivity;
import com.iconiz.tokenunion.ui.capital.view.fragment.CapitalHomeFragment;
import com.iconiz.tokenunion.ui.find.FindHomeFragment;
import com.iconiz.tokenunion.ui.invite.view.fragment.InviteHomeFragment;
import com.iconiz.tokenunion.ui.mine.view.fragment.MineHomeFragment;
import com.iconiz.tokenunion.ui.wallet.view.fragment.WalletHomeFragment;
import com.iconiz.tokenunion.utils.ToastUtils;
import com.iconiz.tokenunion.widget.XFragmentTabHost;
import com.yidaichu.android.common.http.HttpManager;

public class MainActivity extends BaseActivity implements Runnable {

    private final long TIME_LIMIT = 3000;

    private final int REQUEST_PERMISSION_SCAN = 101;
    private final int REQUEST_PERMISSION_INSTALL = 102;
    public static final int ACTION_NOTICE_CLICK = 1000;
    public static boolean isShielded = false;

    private XFragmentTabHost mTabHost;
    private TextView[] mHintViews;

    private Handler handler = new Handler();

    private int backPressedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);

        View tabLayout;
        ImageView tabImgView;
        TextView tabTxtView;

        mHintViews = new TextView[5];
        int[] tabIcons = {
                R.drawable.selector_tab_capital,
                R.drawable.selector_tab_wallet,
                R.drawable.selector_tab_find,
                R.drawable.selector_tab_invite,
                R.drawable.selector_tab_mine};
        int[] tabTxts = {
                R.string.tab_capital,
                R.string.tab_wallet,
                R.string.tab_find,
                R.string.tab_invite,
                R.string.tab_mine};
        String[] tabTags = {"capital",
                "wallet",
                "find",
                "invite",
                "mine"};
        Class[] tabClasses = {
                CapitalHomeFragment.class,
                WalletHomeFragment.class,
                FindHomeFragment.class,
                InviteHomeFragment.class,
                MineHomeFragment.class};

        int tabCount = tabIcons.length;

        for (int i = 0; i < tabCount; i++) {
            tabLayout = getLayoutInflater().inflate(R.layout.layout_main_tab, null, false);
            tabImgView = tabLayout.findViewById(R.id.layout_main_tab_img);
            tabTxtView = tabLayout.findViewById(R.id.layout_main_tab_txt);
            mHintViews[i] = tabLayout.findViewById(R.id.layout_main_tab_hint);
            if (tabIcons[i] != 0) {
                tabTxtView.setText(tabTxts[i]);
                tabImgView.setImageResource(tabIcons[i]);
            }
            mTabHost.addTab(mTabHost.newTabSpec(tabTags[i]).setIndicator(tabLayout), tabClasses[i], null);
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) { }
        });

        int tabWidth = getResources().getDisplayMetrics().widthPixels / tabCount;
        TabWidget tabWidget = mTabHost.getTabWidget();
        for (int i = 0; i < tabCount; i++) {
            tabWidget.getChildTabViewAt(i).getLayoutParams().width = tabWidth;
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedCount < 1) {
            handler.postDelayed(this, TIME_LIMIT);
            ToastUtils.showToast(this, R.string.press_again_to_exit);
            backPressedCount++;
        } else {
            handler.removeCallbacks(this);
            finish();
        }
    }

    @Override
    public void run() {
        backPressedCount = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpManager.getInstance().stopAllSession();
    }

}
