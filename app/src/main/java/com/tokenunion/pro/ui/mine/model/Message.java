package com.tokenunion.pro.ui.mine.model;

import java.io.Serializable;

public class Message  implements Serializable {

    public static final int TYPE_MONEY = 0;
    public static final int TYPE_UPGRADE = 1;
    public static final int TYPE_INVITE = 2;

    private int type;
    private long createTime;
    private String title;
    private String content;
    private String currency;
    private float money;

    public Message() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
