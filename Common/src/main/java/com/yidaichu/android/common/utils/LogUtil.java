package com.yidaichu.android.common.utils;

import android.provider.SyncStateContract;

import com.yidaichu.android.common.BuildConfig;

/**
 * Create by: xiaohansong
 * Time: 2019-07-22 01:26
 * <p>
 * Description: log 工具类
 */
public class LogUtil {
	public static boolean isDebug = BuildConfig.DEBUG;

	public static void v(String tag, String msg) {
		if (isDebug) {
			if (null != msg) {
				android.util.Log.v(tag, msg);
			}
		}
	}

	public static void v(String tag, String msg, Throwable t) {
		if (isDebug) {
			if (null != msg) {
				android.util.Log.v(tag, msg, t);
			}
		}
	}

	public static void d(String tag, String msg) {
		if (isDebug) {
			if (null != msg) {
				android.util.Log.d(tag, msg);
			}
		}
	}

	public static void d(String tag, String msg, Throwable t) {
		if (isDebug) {
			if (null != msg) {
				android.util.Log.d(tag, msg, t);
			}
		}
	}

	public static void i(String tag, String msg) {
		if (null != msg) {
			android.util.Log.i(tag, msg);
		}
	}

	public static void i(String tag, String msg, Throwable t) {
		if (null != msg) {
			android.util.Log.i(tag, msg, t);
		}
	}

	public static void w(String tag, String msg) {
		if(null != msg) {
			android.util.Log.w(tag, msg);
		}
	}

	public static void w(String tag, String msg, Throwable t) {
		if (null != msg) {
			android.util.Log.w(tag, msg, t);
		}
	}

	public static void e(String tag, String msg) {
		if(null != msg) {
			android.util.Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable t) {
		if (null != msg) {
			android.util.Log.e(tag, msg, t);
		}
	}
}
