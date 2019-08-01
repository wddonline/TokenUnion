package com.yidaichu.android.common.http.error;

/**
 * Created by wangdd on 16-11-26.
 */

public class ErrorCode {

    public static final int UNKNOW_ERROR = -1;//未知错误

    public static final int AUTH_FAILURE_ERROR = -2;//如果在做一个HTTP的身份验证，可能会发生这个错误。

    public static final int NETWORK_ERROR = -2;//Socket关闭，服务器宕机，DNS错误都会产生这个错误。

    public static final int NO_CONNECTION_ERROR = -4;//这个是客户端没有网络连接。

    public static final int PARSE_ERROR = -5;//接收到的JSON是畸形

    public static final int SERVER_ERROR = -6;//服务器的响应的一个错误，最有可能的4xx或5xx HTTP状态代码。

    public static final int TIMEOUT_ERROR = -7;////Socket超时，服务器太忙或网络延迟会产生这个异常。

}
