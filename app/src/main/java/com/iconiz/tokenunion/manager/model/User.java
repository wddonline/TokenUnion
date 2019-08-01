package com.iconiz.tokenunion.manager.model;

public class User {

    public static final int LEVEL_YOUNG = 0;
    public static final int LEVEL_PREFERRED = 1;
    public static final int LEVEL_ELITE = 2;
    public static final int LEVEL_RESERVE = 3;
    public static final int LEVEL_WORLD = 4;
    public static final int LEVEL_INFINITE = 5;

    private int userLevel = LEVEL_YOUNG;

    public User() {
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
}
