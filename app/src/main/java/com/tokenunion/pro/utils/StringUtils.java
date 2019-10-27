package com.tokenunion.pro.utils;

/**
 * Create by: xiaohansong
 * Time: 2019-07-31 15:18
 * -
 * Description:
 */
public class StringUtils {
    public static boolean isEmpty(String strData){
        if(null == strData || strData.isEmpty()){
            return true;
        }
        return false;
    }

    public static String getAbsoluteNum(float num, int remain) {
        String result = String.format("%1$." + remain + "f", num);
        int end = result.length();
        char ch;
        for (int i = result.length() - 1; i > 0; i--) {
            ch = result.charAt(i);
            if (ch == '.') {
                end = i;
                break;
            } else if(ch != '0') {
                end = i + 1;
                break;
            }
        }
        if (end != -1) {
            result = result.substring(0, end);
        }
        return result;
    }

}
