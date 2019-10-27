package com.tokenunion.pro.utils;

import android.content.Intent;
import android.graphics.Bitmap;

import androidx.core.app.ActivityCompat;

import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.ui.login.view.LoginActivity;
import com.tokenunion.pro.ui.main.MainActivity;
import com.tokenunion.pro.widget.RoundNetworkImageView;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.Collections;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-07-31 21:23
 * -
 * Description: 业务相关的工具类
 */
public class BusinessUtils {
    /**
     * 显示圆形用户头像
     * @param headerView
     */
    public static void displayUserHeadImage(RoundNetworkImageView headerView){
        if(UserAccount.getInstance().isLogin()) {
            if(!StringUtils.isEmpty(UserAccount.getInstance().getUserBean().getAvatarPath())){
                headerView.setImageUri(UserAccount.getInstance().getUserBean().getAvatarPath());
                    // "http://m.360buyimg.com/pop/jfs/t25198/35/225724054/50007/53ddf0bc/5b696c57Nc3885dbe.jpg");
            }
        }
    }

    /**
     * 将用户星级的名称转化成级别。比如 vip0 -> 0
     * @param levelCode
     * @return
     */
    public static int getLevelId(String levelCode){
        int level = -1;
        if(null == levelCode || levelCode.isEmpty()){
            return level;
        }

        try {
            level = Integer.parseInt(levelCode.substring(levelCode.length() - 1)); // 取最后一位，转化成int
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return level;
    }

    /**
     * 登录token失效，跳转登录页
     */
    public static void loginTimeout(){
        LogUtil.w("loginTimeout", "loginTimeout");
        Intent intent = new Intent(MainActivity.getInstance(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityCompat.startActivity(MainActivity.getInstance(), intent, null);
    }

    /**
     * 生成二维码
     * @param strData
     * @return
     */
    public static Bitmap createQRCode(String strData){
        //生成二维码
        Bitmap bitmap = CodeUtils.createImage(strData, 200, 200, null);
        return bitmap;
    }

    /**
     * 集合比较
     * @param <T>
     * @param a
     * @param b
     * @return
     */
    public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if(a.size() != b.size())
            return false;
        Collections.sort(a);
        Collections.sort(b);
        for(int i=0;i<a.size();i++){
            if(!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }
}
