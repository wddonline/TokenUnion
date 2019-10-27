package com.tokenunion.pro.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.yidaichu.android.common.utils.LogUtil;

import java.lang.reflect.Method;

public class AppUtils {
    private static final String TAG = AppUtils.class.getSimpleName();

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static boolean isNetworkAvalible(Context context) {
        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //获取版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pInfo = null;

        try {
            PackageManager pManager = context.getPackageManager();
            pInfo = pManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pInfo;
    }

    public static boolean isKeyboardShow(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getWindow().getCurrentFocus();
        return  imm.isActive(view);
    }

    public static void showKeyboard(Activity activity, boolean isShow) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getWindow().getCurrentFocus();
        if (isShow) {
            if (imm.isActive(view)) return;
            imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } else {
            if (imm.isActive(view)) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    public static void copyText(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, text);
        clipboard.setPrimaryClip(clipData);
    }

    /**
     * 将TextView设置粗斜体
     * @param textView
     */
    public static void setItalicFont(TextView textView){
        String strData = textView.getText().toString();
        textView.setText(strData, TextView.BufferType.EDITABLE);
        /**
         * 要设置文本的背景色，
         * 必须将文本设置成BufferType.SPANNABLE,BufferType.EDITABLE
         */
        Spannable sp = (Spannable) textView.getText();

        //设置红色背景
//        sp.setSpan(new BackgroundColorSpan(Color.RED), 3, 8,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC),
        sp.setSpan(new StyleSpan(Typeface.ITALIC),
                0, strData.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //设置斜体
        textView.setText(sp);
    }

    /**
     * 设置TextView的字体，默认TimesNewRomanBoldItalic字体
     * @param context
     * @param textView
     */
    public static void setCustomFont(Context context, TextView textView){
        //得到AssetManager
        AssetManager mgr = context.getAssets();

        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/TimesNewRomanBoldItalic.ttf");
        textView.setTypeface(tf);
    }

    /**
     * 设置TextView的字体。指定字体文件
     * @param context
     * @param textView
     * @param fontFilePath 字体文件路径，放到asserts/fonts下。比如"fonts/TimesNewRomanBoldItalic.ttf"
     */
    public static void setCustomFont(Context context, TextView textView, String fontFilePath){
        //得到AssetManager
        AssetManager mgr = context.getAssets();

        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, fontFilePath);
        textView.setTypeface(tf);
    }

    /**
     * TextView文本颜色突出显示，金色
     * @param textView
     * @param strData
     * @param start
     * @param end
     */
    public static void changeTextViewTextColor(TextView textView, String strData, int start, int end){
        SpannableString span = new SpannableString(strData);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#CAAC89"));
        span.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(span);
    }

    /**
     * 卸载指定包名的应用
     * @param packageName
     */
    public static boolean uninstallApp(Context context, String packageName) {
        boolean installed = isAppInstalled(context, packageName);
        LogUtil.d(TAG, "isAppInstalled:"+ installed);
        if (installed) {
            Uri packageURI = Uri.parse("package:".concat(packageName));
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(packageURI);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * 判断该包名的应用是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        LogUtil.d(TAG, "isAppInstalled");
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.MATCH_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(TAG, "NameNotFoundException, "+e.toString());
        }
        return false;
    }
}
