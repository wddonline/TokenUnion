package com.tokenunion.pro.config;

import com.alibaba.fastjson.JSONObject;
import com.tokenunion.pro.app.TUApplication;
import com.tokenunion.pro.ui.login.model.LoginBean;
import com.tokenunion.pro.utils.Base64Utils;
import com.tokenunion.pro.utils.LogUtil;
import com.tokenunion.pro.utils.SharedPreferenceUtils;

/**
 * Create by: xiaohansong
 * Time: 2019-07-22 01:26
 * <p>
 * Description: 用户登录状态信息
 */
public class UserAccount {
    private static final String TAG = UserAccount.class.getSimpleName();
    private static UserAccount sInstance;

    private LoginBean mUserBean;


    public LoginBean getUserBean() {
        return mUserBean;
    }

    public void setUserBean(LoginBean userBean) {
        this.mUserBean = userBean;
        saveUserInfo(userBean);
    }

    private UserAccount() {
        // for test
//        mUserBean = new UserBean();
        mUserBean = readUserInfo();
    }

    public synchronized static UserAccount getInstance() {
        if (null == sInstance) {
            synchronized (UserAccount.class) {
                if (null == sInstance) {
                    sInstance = new UserAccount();
                }
            }
        }
        return sInstance;
    }


    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return (mUserBean != null);
    }
    /**
     * 持久化存储用户信息。存储到SP中
     */
    private boolean saveUserInfo(LoginBean user) {
        if (null == user) {
            SharedPreferenceUtils.remove(TUApplication.INSTANCE, SharedPrefKeys.USER_INFO_KEY);
            return false;
        }

        String userInfo = JSONObject.toJSONString(user);
        String userInfoEnc = Base64Utils.encode(userInfo);
        SharedPreferenceUtils.put(TUApplication.INSTANCE, SharedPrefKeys.USER_INFO_KEY, userInfoEnc);
        return true;
    }

    public boolean saveUserInfo() {
        return saveUserInfo(mUserBean);
    }

    /**
     * 从存储中读取用户信息
     *
     * @return
     */
    private LoginBean readUserInfo() {
        LoginBean userBean = null;
        String userInfoEnc = (String) SharedPreferenceUtils.get(TUApplication.INSTANCE, SharedPrefKeys.USER_INFO_KEY, "");
        if (userInfoEnc != "") {
            try {
                String userInfo = Base64Utils.decode(userInfoEnc);
                userBean = JSONObject.parseObject(userInfo, LoginBean.class);
            } catch (Exception ex) {
                ex.printStackTrace();
                LogUtil.w(TAG, "read user info failed!");
            }
        }
        return userBean;
    }

}
