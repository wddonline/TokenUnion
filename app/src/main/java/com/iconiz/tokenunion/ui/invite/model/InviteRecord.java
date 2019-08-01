package com.iconiz.tokenunion.ui.invite.model;

public class InviteRecord {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_STAR = 1;
    public static final int TYPE_JADE = 2;
    public static final int TYPE_GOLD = 3;
    public static final int TYPE_TITANIUM = 4;
    public static final int TYPE_PLATINUM = 5;
    public static final int TYPE_DIAMOND = 6;

    private int type = TYPE_NORMAL;
    private String value;

    public InviteRecord(String value) {
        this.value = value;
    }

    public InviteRecord(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
