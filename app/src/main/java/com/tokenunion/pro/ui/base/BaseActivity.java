package com.tokenunion.pro.ui.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.tokenunion.pro.R;
import com.tokenunion.pro.app.TUApplication;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.Configs;
import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.ui.login.UserApi;
import com.tokenunion.pro.ui.login.view.SafetySettingsActivity;
import com.tokenunion.pro.ui.mine.MineApi;
import com.tokenunion.pro.ui.mine.model.MessageBean;
import com.tokenunion.pro.ui.mine.model.VersionCheckBean;
import com.tokenunion.pro.utils.EventBusUtils;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.SharedPreferenceUtils;
import com.tokenunion.pro.utils.StringUtils;
import com.tokenunion.pro.utils.ToastUtils;
import com.tokenunion.pro.utils.language.LanguageType;
import com.tokenunion.pro.utils.language.LanguageUtil;
import com.tokenunion.pro.utils.language.SpUtil;
import com.tokenunion.pro.widget.AppNewVersionDlg;
import com.tokenunion.pro.zxing.android.CaptureActivity;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.tokenunion.pro.utils.AppUtils;
import com.yidaichu.android.common.view.statusbar.StatusBarCompat;

import static com.tokenunion.pro.config.SharedPrefKeys.HAS_NEW_MESSAGE_KEY;
import static com.tokenunion.pro.config.SharedPrefKeys.LATEST_MESSAGE_ID_KEY;
import static com.tokenunion.pro.utils.EventBusUtils.EVENT_NO_NEW_APP_VERSION;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    // 上一次的最新的messageId，在MessageActivity中显示之后更新
    private static int sMaxlastmessageid = -1;
    // 消息分页获取数据的page size
    protected static final int MESSAGE_PAGE_SIZE = 10;

    public ActivityFragmentActive mActive = new ActivityFragmentActive(this);
    protected final int REQUEST_CODE_SCAN = 0x0000;
    protected final int REQUEST_CAMARA_PERMISSION = 0x0001;

    /**
     * 此方法先于 onCreate()方法执行
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        //获取我们存储的语言环境 比如 "en","zh",等等
        String language = SpUtil.getInstance(TUApplication.INSTANCE).getString(SpUtil.LANGUAGE);
        if(TextUtils.isEmpty(language)){
//            language = "ch";
            language = "en";
            SpUtil.getInstance(TUApplication.INSTANCE).putString(SpUtil.LANGUAGE, language); // 如果没有设置过，默认英文
        }
        Configs.setCurrentLanguage(LanguageType.getLanguageName(language));
        /**
         * attach对应语言环境下的context
         */
        super.attachBaseContext(LanguageUtil.attachBaseContext(newBase, language));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
		setStatusBarLightMode();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected abstract void initData();

    protected abstract void initViews();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActive.destroy();
    }

    public final void setStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    public final void setTranslucent(boolean translucent) {
        StatusBarCompat.setTranslucent(getWindow(), translucent);
    }

    public final void setTransparent(boolean translucent) {
        StatusBarCompat.setTransparent(getWindow(), translucent);
    }

    public final int setStatusBarLightMode() {
        return StatusBarCompat.setStatusBarLightMode(this);
    }

    public void jumpToActivity(Class<? extends Activity> clazz) {
        jumpToActivity(clazz, null);
    }

    public void jumpToActivity(Class<? extends Activity> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    public void jumpToActivity(Class<? extends Activity> clazz, View sharedView, String sharedElementName ) {
        jumpToActivity(clazz, null, sharedView, sharedElementName);
    }

    public void jumpToActivity(Class<? extends Activity> clazz, Bundle extras, View sharedView, String sharedElementName ) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        ActivityCompat.startActivity(this, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, sharedElementName).toBundle());
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, int requestCode) {
        jumpToActivityForResult(clazz, null, requestCode);
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, Bundle extras, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivityForResult(intent, requestCode);
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, View sharedView, String sharedElementName, int requestCode) {
        jumpToActivityForResult(clazz, null, sharedView, sharedElementName, requestCode);
    }

    public void jumpToActivityForResult(Class<? extends Activity> clazz, Bundle extras, View sharedView, String sharedElementName, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        ActivityCompat.startActivityForResult(this, intent,  requestCode,ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, sharedElementName).toBundle());
    }

    protected boolean verifyTradePasswordAndSet(){
        if(UserAccount.getInstance().isLogin()){
            if(!UserAccount.getInstance().getUserBean().isSetTradePasswd()){
                jumpToActivity(SafetySettingsActivity.class);
                return false;
            }
        }return true;
    }


    /**
     * 发送短信验证码请求，获取短信验证
     * @param context
     * @param phone
     * @param country
     */
    protected void getSmsCode(Context context, String phone, String country) {
        UserApi.getSmsCode(phone, country, null, mActive,
                new ApiRequestCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        LogUtil.d(context.getClass().getSimpleName(), "getSmsCode() send ok");
                    }

                    @Override
                    public void onFailed(String errCode, final String errMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(context, errMessage);
                            }
                        });

                    }
                });
    }

    /**
     * 检查、获取摄像头权限
     */
    protected void checkCarmeraPermission() {
        PackageManager pkgManager = getPackageManager();
        // 获取摄像机用于获取摄像机 设备信息  CAMERA
        boolean cameraSatePermission =
                pkgManager.checkPermission(Manifest.permission.CAMERA, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= 23 && !cameraSatePermission) {
            requestCarmeraPermission();
        } else {
            Intent intents = new Intent(this, CaptureActivity.class);
            startActivityForResult(intents, REQUEST_CODE_SCAN);
        }
    }


    private void requestCarmeraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMARA_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CAMARA_PERMISSION) {
            Intent intents = new Intent(this, CaptureActivity.class);
            startActivityForResult(intents, REQUEST_CODE_SCAN);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 检测新版本
     */
    protected void checkVersion(){
//        String versionName = "v.1.1.0";//AppUtils.getVersionName(this); // for test
        String versionName = AppUtils.getVersionName(this);
        MineApi.checkAppVersion(versionName, mActive, new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                final VersionCheckBean bean = (VersionCheckBean)object;
                if(null == object || StringUtils.isEmpty(bean.getDownUrl())){
                    // 如果数据为null或者下载地址为空，则不认为有新版本。
                    LogUtil.i(TAG, "checkAppVersion, data emperty.");
                    EventBusUtils.post(EVENT_NO_NEW_APP_VERSION);
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.i(TAG, "有新版本。"+ bean);
                        AppNewVersionDlg dlg = new AppNewVersionDlg(BaseActivity.this);
                        dlg.setVersionCheckBean(bean);
                        dlg.show();
                    }
                });

            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.i(TAG, "没有新版本。"+ errMessage);
                        EventBusUtils.post(EVENT_NO_NEW_APP_VERSION);
                    }
                });

            }
        });
    }

    /**
     * 检查是否有新消息
     */
    protected void checkNewMessage(){
        String mid ;//= (-1 == sMaxlastmessageid ? "" : sMaxlastmessageid + "");
        mid = SharedPreferenceUtils.getString(BaseActivity.this, LATEST_MESSAGE_ID_KEY, "");
        MineApi.getMessageCenterListData(mid, "", 1, mActive , new ApiRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                MessageBean messageBean = (MessageBean) object;
                if(null == messageBean || null == messageBean.getMessageList() || messageBean.getMessageList().isEmpty()){
                    LogUtil.e(TAG, "message data is empty.");
                    // 设置状态为"没有新消息"
                    SharedPreferenceUtils.saveBoolean(BaseActivity.this, HAS_NEW_MESSAGE_KEY, false);
                    EventBusUtils.post(new EventBusUtils.CommonEvent(EventBusUtils.EVENT_NO_NEW_MESSAGE));
                    return;
                }else {
                    SharedPreferenceUtils.saveBoolean(BaseActivity.this, HAS_NEW_MESSAGE_KEY, true);
                    // 有新消息啦
                    EventBusUtils.post(new EventBusUtils.CommonEvent(EventBusUtils.EVENT_HAS_NEW_MESSAGE));
                }
            }

            @Override
            public void onFailed(String errCode, final String errMessage) {
                LogUtil.w(TAG, "checkNewMessage() failed! errMessage = "+ errMessage);
            }
        });
    }

}