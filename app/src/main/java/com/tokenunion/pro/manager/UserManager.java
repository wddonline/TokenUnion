package com.tokenunion.pro.manager;

import android.content.Context;

import com.tokenunion.pro.app.TUApplication;
import com.tokenunion.pro.manager.model.User;

public class UserManager {

    private static UserManager instance;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager(TUApplication.INSTANCE);
        }
        return instance;
    }

    private User mUser;

    private UserManager(Context context) {
        mUser = new User();
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public User getUser() {
        return mUser;
    }

    public boolean isUserLogined() {
        return mUser != null;
    }
}
