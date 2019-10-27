package com.tokenunion.pro.ui.mine.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-08-09 18:05
 * -
 * Description: 新版本APP检测接口返回的数据
 */
public class VersionCheckBean  implements Serializable {

    /**
     * id : 1
     * oldVersion : v.1.1.0
     * toVersion : v2.10.0
     * isForce : 0
     * downUrl : https://any-apk.s3.ap-northeast-2.amazonaws.com/anypocket_app_test.apk
     * content : 本次更新为新增部分内容
     * ctime : 2019-08-08T09:07:43
     * mtime :
     * version : 0
     * validTime : 2019-08-08T00:00:00
     */

    private int id;
    private String oldVersion;
    private String toVersion;
    // 非强制：0 强制更新：1
    private int isForce;
    private String downUrl;
    private String content;
    private String ctime;
    private String mtime;
    private int version;
    private String validTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldVersion() {
        return oldVersion;
    }

    public void setOldVersion(String oldVersion) {
        this.oldVersion = oldVersion;
    }

    public String getToVersion() {
        return toVersion;
    }

    public void setToVersion(String toVersion) {
        this.toVersion = toVersion;
    }

    public int getIsForce() {
        return isForce;
    }

    public void setIsForce(int isForce) {
        this.isForce = isForce;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    @Override
    public String toString() {
        return "VersionCheckBean{" +
                "id=" + id +
                ", oldVersion='" + oldVersion + '\'' +
                ", toVersion='" + toVersion + '\'' +
                ", isForce=" + isForce +
                ", downUrl='" + downUrl + '\'' +
                ", content='" + content + '\'' +
                ", ctime='" + ctime + '\'' +
                ", mtime='" + mtime + '\'' +
                ", version=" + version +
                ", validTime='" + validTime + '\'' +
                '}';
    }
}
