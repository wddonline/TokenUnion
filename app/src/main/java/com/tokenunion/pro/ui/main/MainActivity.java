package com.tokenunion.pro.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.invite.view.fragment.InviteHomeFragment;
import com.tokenunion.pro.R;
import com.tokenunion.pro.ui.base.BaseFragment;
import com.tokenunion.pro.ui.capital.view.fragment.CapitalHomeFragment;
import com.tokenunion.pro.ui.find.view.fragment.FindHomeFragment;
import com.tokenunion.pro.ui.mine.view.fragment.MineHomeFragment;
import com.tokenunion.pro.ui.wallet.view.fragment.WalletHomeFragment;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.widget.NormalDlg;
import com.tokenunion.pro.widget.XFragmentTabHost;
import com.yidaichu.android.common.http.HttpManager;
import com.tokenunion.pro.utils.AppUtils;

public class MainActivity extends BaseActivity implements Runnable {

    private String TAG = MainActivity.class.getSimpleName();
    private final long TIME_LIMIT = 3000;
    private static MainActivity sMainActivity;

    public static MainActivity getInstance() {
        return sMainActivity;
    }

    private final int REQUEST_PERMISSION_SCAN = 101;
    private final int REQUEST_PERMISSION_INSTALL = 102;
    public static final int ACTION_NOTICE_CLICK = 1000;
    public static boolean isShielded = false;

    private XFragmentTabHost mTabHost;
    private TextView[] mHintViews;

    private Handler handler = new Handler();

    private int backPressedCount = 0;
    // 当前选中的Fragment
    private BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();

        sMainActivity = this;
        uinstallOld();
    }

    private void uinstallOld() {
        String strOldPackageName = "com.iconiz.anypocket";
        if (!AppUtils.isAppInstalled(this, strOldPackageName)) {
            return;
        }

        final NormalDlg dlg = new NormalDlg(this);
        dlg.setTitle(getString(R.string.app_old_version_exist_title) + AppUtils.getVersionName(this));
        dlg.setMessage(getString(R.string.app_old_version_message));
        dlg.setYesClickListener(getString(R.string.app_old_version_uninstall), new NormalDlg.OnYesClickListener() {
            @Override
            public void onYesClick() {
                dlg.dismiss();
                AppUtils.uninstallApp(MainActivity.this, strOldPackageName);
            }
        });

        dlg.setNoClickListener(getString(R.string.app_old_version_cancel), new NormalDlg.OnNoClickListener() {
            @Override
            public void onNoClick() {
                dlg.dismiss();
            }
        });

        dlg.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mCurrentFragment) {
            mCurrentFragment.forceReloadData();
        }
        checkVersion();
        checkNewMessage();
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
        String[] tabTags = {
                CapitalHomeFragment.class.getSimpleName(),
                WalletHomeFragment.class.getSimpleName(),
                FindHomeFragment.class.getSimpleName(),
                InviteHomeFragment.class.getSimpleName(),
                MineHomeFragment.class.getSimpleName()};

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
            public void onTabChanged(String tabId) {
                LogUtil.d(TAG, "onTabChanged, " + tabId);
                for (int i = 0; i < mTabHost.getFragmentManager().getFragments().size(); i++) {
                    if (tabTags[i].equals(tabId)) {
                        if (mTabHost.getFragmentManager().getFragments().get(i) instanceof BaseFragment) {
                            mCurrentFragment = (BaseFragment) mTabHost.getFragmentManager().getFragments().get(i);
                            mCurrentFragment.forceReloadData();
                        } else {
                            if (tabId.contains("wallet")) {
                                LogUtil.i(TAG, "not BaseFragment, " + tabId);
                            }
                        }

                        break;
                    }
                }

                checkNewMessage();
            }
        });

        int tabWidth = getResources().getDisplayMetrics().widthPixels / tabCount;
        TabWidget tabWidget = mTabHost.getTabWidget();
        for (int i = 0; i < tabCount; i++) {
            tabWidget.getChildTabViewAt(i).getLayoutParams().width = tabWidth;
        }
    }

    /**
     * 从外部切换tab
     *
     * @param index
     */
    public void swithToFragment(int index) {
        mTabHost.setCurrentTab(index);
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
