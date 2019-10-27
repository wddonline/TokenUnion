package com.yidaichu.android.common.http.error;

/**
 * Created by wangdd on 16-11-26.
 */

public class HttpError extends Throwable {

    private int errorCode;
    private String errorMsg;
    private String extra;
    private int statusCode = -1;

    public HttpError() {
    }

    public HttpError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public HttpError(int errorCode, String errorMsg, int statusCode) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.statusCode = statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "HttpError{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", extra='" + extra + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
