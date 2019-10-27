package com.tokenunion.pro.ui.mine.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-08-26 16:01
 * -
 * Description: 消息中心接口返回的list数据
 */
public class MessageBean implements Serializable {

    /**
     * totalNum : 1
     * messageList : [{"id":3,"title":"提现手续费","content":"ANY手续费调整为：0.3%，单笔至少1000ANY，少于提币额无法到账","type":1,"picUrl":"https://treapp.tech/img/eternalGate.png","dataType":"VERSION_UPDATE","showTime":"2019-08-26T02:00:00","language":"CN"}]
     */

    private int totalNum;
    private List<MessageListBean> messageList;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<MessageListBean> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageListBean> messageList) {
        this.messageList = messageList;
    }

    public static class MessageListBean implements Serializable {
        /**
         * id : 3
         * title : 提现手续费
         * content : ANY手续费调整为：0.3%，单笔至少1000ANY，少于提币额无法到账
         * type : 1
         * picUrl : https://treapp.tech/img/eternalGate.png
         * dataType : VERSION_UPDATE
         * showTime : 2019-08-26T02:00:00
         * language : CN
         */

        private int id;
        private String title;
        private String content;
        private int type;
        private String picUrl;
        private String dataType;
        private String showTime;
        private String language;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }
}
