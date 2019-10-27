package com.tokenunion.pro.ui.welcome;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.permission.PermissionListener;
import com.tokenunion.pro.permission.PermissionManager;
import com.tokenunion.pro.permission.Rationale;
import com.tokenunion.pro.permission.RationaleListener;
import com.tokenunion.pro.permission.SettingDialog;
import com.tokenunion.pro.ui.base.BaseActivity;
import com.tokenunion.pro.ui.login.view.LoginActivity;
import com.tokenunion.pro.ui.main.MainActivity;
import com.anypocket.pro.R;

import java.util.List;

public class SplashActivity extends BaseActivity implements PermissionListener, Runnable {

    private final int REQUEST_PERMISSION = 1000;

    private boolean isCheckRequired = false;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(Color.WHITE);
        setStatusBarLightMode();
        setContentView(R.layout.activity_splash);
        initViews();
        checkPermission();
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }

    private void checkPermission() {
        PermissionManager.with(this)
                .requestCode(REQUEST_PERMISSION)
                .permission(Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        PermissionManager.rationaleDialog(SplashActivity.this, rationale).show();
                    }
                }).send();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCheckRequired) {
            checkPermission();
            isCheckRequired = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onSucceed(int requestCode, List<String> grantedPermissions) {
        mHandler.postDelayed(this, 1500);
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        if (PermissionManager.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            PermissionManager.defaultSettingDialog(this)
                    .setSettingDialogListener(new SettingDialog.SettingDialogListener() {
                        @Override
                        public void onSettingClicked() {
                            isCheckRequired = true;
                        }

                        @Override
                        public void onCancelClicked() {
                            finish();
                        }
                    }).show();
        } else {
            finish();
        }
    }


    @Override
    public void run() {
        if(UserAccount.getInstance().isLogin()){
            jumpToActivity(MainActivity.class);
        }else {
            jumpToActivity(LoginActivity.class);
        }
        finish();
    }
}
