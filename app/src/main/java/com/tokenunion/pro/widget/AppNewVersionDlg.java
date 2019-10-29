package com.tokenunion.pro.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tokenunion.pro.ui.mine.model.VersionCheckBean;
import com.tokenunion.pro.R;
import com.tokenunion.pro.app.AppUpDateManage;
import com.tokenunion.pro.utils.StringUtils;

import static com.tokenunion.pro.R.style.BottomDialog;

/**
 * Create by: xiaohansong
 * Time: 2019-08-09 18:15
 * -
 * Description: App新版本提示
 */
public class AppNewVersionDlg extends Dialog implements View.OnClickListener {

    private Context mContext;

    // 升级信息的bean
    private VersionCheckBean mVersionCheckBean;

    public void setVersionCheckBean(VersionCheckBean versionCheckBean) {
        mVersionCheckBean = versionCheckBean;
    }

    public AppNewVersionDlg(@NonNull Context context) {
        super(context, BottomDialog);//风格主题
        mContext = context;
    }

    public AppNewVersionDlg(@NonNull Context context, int themeResId) {
        super(context, BottomDialog);//风格主题
        mContext = context;
    }

    protected AppNewVersionDlg(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_new_version_dlg_layout);//自定义布局
        //按空白处不能取消
//        setCanceledOnTouchOutside(false);

        initView();
    }

    private void initView(){
        findViewById(R.id.app_new_version_dlg_layout_cancel).setOnClickListener(this);
        findViewById(R.id.app_new_version_dlg_layout_upgrade).setOnClickListener(this);

        if(null != mVersionCheckBean) {
            // 标题
            TextView titleView = findViewById(R.id.app_new_version_dlg_layout_title);
            titleView.setText(mContext.getText(R.string.mine_app_version_tips_title)+ " "+ mVersionCheckBean.getToVersion());

            // 中线内容
            TextView messageView = findViewById(R.id.app_new_version_dlg_layout_message);
            messageView.setText(mVersionCheckBean.getContent());

            if(1 == mVersionCheckBean.getIsForce()){
                // 是否强制升级。非强制：0 强制更新：1
                findViewById(R.id.app_new_version_dlg_layout_cancel).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.app_new_version_dlg_layout_cancel:
                dismiss();
                break;

            case R.id.app_new_version_dlg_layout_upgrade:
                // ToastUtils.showToast(this.getContext(), "升级");
                dismiss();
                if(null != mVersionCheckBean && !StringUtils.isEmpty(mVersionCheckBean.getDownUrl())) {
                    // downloadAndInstallBySystemView(mVersionCheckBean.getDownUrl()); // 系统下载

                    AppUpDateManage.loadNewVersionProgress(mContext, mVersionCheckBean.getDownUrl());
                    // AppUpDateManage.installApk(mContext, new File("/storage/emulated/0/Download/1565428259509any_app.apk"));
                }
                break;

            default:
                break;
        }
    }

    /**
     * 直接用系统的方式下载及安装。不用自己下载
     */
    private void downloadAndInstallBySystemView(String url){
         Uri uri = Uri.parse(url);
         Intent intent = new Intent(Intent.ACTION_VIEW, uri);
         mContext.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // 如果强制升级，不响应back键
        if(null != mVersionCheckBean
                && 1 == mVersionCheckBean.getIsForce()){
            return;
        }
        super.onBackPressed();
    }
}
