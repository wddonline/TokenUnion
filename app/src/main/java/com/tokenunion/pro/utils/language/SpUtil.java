package com.tokenunion.pro.utils.language;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dumingwei on 2018/6/3 0003.
 */
public class SpUtil {

    public static final String LANGUAGE = "language"; // 系统语言
    public static final String LANGUAGE_ID = "language_ID"; // 语言序号
    public static final String LANGUAGE_NAME = "language_NAME"; // 接口语言
    private static final String SP_NAME = "poemTripSpref";
    private static SpUtil spUtil;
    private static SharedPreferences hmSpref;
    private static SharedPreferences.Editor editor;

    private SpUtil(Context context) {
        hmSpref = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = hmSpref.edit();
    }

    public static SpUtil getInstance(Context context) {
        if (spUtil == null) {
            synchronized (SpUtil.class) {
                if (spUtil == null) {
                    spUtil = new SpUtil(context);
                }
            }
        }
        return spUtil;
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return hmSpref.getString(key,"");
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return hmSpref.getInt(key,0);
    }

}
