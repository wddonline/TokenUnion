package com.tokenunion.pro.utils;

import android.content.Intent;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;


/**
 * Create by: xiaohansong
 * Time: 2019-08-11 22:00
 * -
 * Description: 该类是用于封装EventBus操作的工具类
 */
public class EventBusUtils {
    private static EventBus mEventBus;

    public static void init() {
        initInternal();
    }

    private static void initInternal() {
        mEventBus = EventBus.getDefault();
    }

    public static void register(Object subscriber) {
        mEventBus.register(subscriber);
    }

    public static void unregister(Object subscriber) {
        mEventBus.unregister(subscriber);
    }

    public static void post(int id) {
        post(new CommonEvent(id));
    }

    public static void post(int id, Bundle data) {
        post(new CommonEvent(id, data));
    }

    public static void post(CommonEvent event) {
        mEventBus.post(event);
    }

    public static void postSticky(Object event) {
        mEventBus.postSticky(event);
    }

    /**
     * 通用事件类型，如无特殊情况请使用该事件类型发送事件
     * {@link CommonEvent#mId}事件类型标识
     * {@link CommonEvent#mData}事件附加数据
     * <p>
     * 注意：不要使用data传递过大的数据量
     */
    public static class CommonEvent {
        private int mId;
        private Bundle mData;

        public Intent intent;

        public CommonEvent() {
        }

        public CommonEvent(int id) {
            this.mId = id;
        }

        public CommonEvent(int id, Bundle data) {
            this.mId = id;
            this.mData = data;
        }

        public int getId() {
            return mId;
        }

        public Bundle getData() {
            return mData;
        }
        public void setId(int id) {
            this.mId = id;
        }

        public void setData(Bundle data) {
            this.mData = data;
        }
    }

    /**
     * 公告信息所用事件类型
     * 因公告信息显示页面的注册时机可能晚于获取到公告的时间，需要使用粘性事件方式
     */
    public static class AppNoticeEvent {

    }

    /**
     * 事件类型标识码统一在此处添加
     */
    public static final int EVENT_ASSET_DATA_UPDATED = 1000; // 资产列表数据已更新
    public static final int EVENT_NO_NEW_APP_VERSION = 1001; // 没有新版本APP（或者已经是最新版）
    public static final int EVENT_HAS_NEW_MESSAGE = 1002; // 有新消息了
    public static final int EVENT_NO_NEW_MESSAGE = 1003; // 没有新消息了
    public static final int EVENT_CAPITAL_OPERATOR_UPDATE = 1004; // "理财"操作记录变化，比如做了买入或赎回操作
}
