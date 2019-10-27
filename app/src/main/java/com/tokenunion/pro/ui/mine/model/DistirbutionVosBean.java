package com.tokenunion.pro.ui.mine.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-07-31 12:03
 * -
 * Description: 邀请团队规模----时间分布:1 规模分布:2 星级分布:3
 */
public class DistirbutionVosBean implements Serializable {

    /**
     * type : 2
     * teamBal : 5000.0000
     * teamNum : 0
     * timeVos : [{"userName":"Lina-国家","signTime":"2019-07-23T11:10:31"},{"userName":"Lina2345","signTime":"2019-07-23T12:27:17"},{"userName":"455677iiii","signTime":"2019-07-24T02:12:59"}]
     * scaleVos : [{"userName":"Lina-国家","teamBal":"352.0000","teamRatio":"7.0400"},{"userName":"Lina2345","teamBal":"1245.0000","teamRatio":"24.9000"},{"userName":"455677iiii","teamBal":"3375.0000","teamRatio":"67.5000"}]
     * levelVos : [{"levelId":1,"levelCode":"vip0","levelName":"小星星","levelNum":0,"teamNum":0,"levelRatio":"0.0000"},{"levelId":2,"levelCode":"vip1","levelName":"玉星级","levelNum":0,"teamNum":0,"levelRatio":"0.0000"}]
     */

    private int type;
    private String teamBal;
    private int teamNum;
    private List<TimeVosBean> timeVos;
    private List<ScaleVosBean> scaleVos;
    private List<LevelVosBean> levelVos;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTeamBal() {
        return teamBal;
    }

    public void setTeamBal(String teamBal) {
        this.teamBal = teamBal;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public List<TimeVosBean> getTimeVos() {
        return timeVos;
    }

    public void setTimeVos(List<TimeVosBean> timeVos) {
        this.timeVos = timeVos;
    }

    public List<ScaleVosBean> getScaleVos() {
        return scaleVos;
    }

    public void setScaleVos(List<ScaleVosBean> scaleVos) {
        this.scaleVos = scaleVos;
    }

    public List<LevelVosBean> getLevelVos() {
        return levelVos;
    }

    public void setLevelVos(List<LevelVosBean> levelVos) {
        this.levelVos = levelVos;
    }

    public static class TimeVosBean implements Serializable {
        /**
         * userName : Lina-国家
         * signTime : 2019-07-23T11:10:31
         */

        private String userName;
        private String signTime;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSignTime() {
            return signTime;
        }

        public void setSignTime(String signTime) {
            this.signTime = signTime;
        }
    }

    public static class ScaleVosBean implements Serializable{
        /**
         * userName : Lina-国家
         * teamBal : 352.0000
         * teamRatio : 7.0400
         */

        private String userName;
        private String teamBal;
        private String teamRatio;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTeamBal() {
            return teamBal;
        }

        public void setTeamBal(String teamBal) {
            this.teamBal = teamBal;
        }

        public String getTeamRatio() {
            return teamRatio;
        }

        public void setTeamRatio(String teamRatio) {
            this.teamRatio = teamRatio;
        }
    }

    public static class LevelVosBean implements Serializable{
        /**
         * levelId : 1
         * levelCode : vip0
         * levelName : 小星星
         * levelNum : 0
         * teamNum : 0
         * levelRatio : 0.0000
         */

        private int levelId;
        private String levelCode;
        private String levelName;
        private int levelNum;
        private int teamNum;
        private String levelRatio;

        public int getLevelId() {
            return levelId;
        }

        public void setLevelId(int levelId) {
            this.levelId = levelId;
        }

        public String getLevelCode() {
            return levelCode;
        }

        public void setLevelCode(String levelCode) {
            this.levelCode = levelCode;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getLevelNum() {
            return levelNum;
        }

        public void setLevelNum(int levelNum) {
            this.levelNum = levelNum;
        }

        public int getTeamNum() {
            return teamNum;
        }

        public void setTeamNum(int teamNum) {
            this.teamNum = teamNum;
        }

        public String getLevelRatio() {
            return levelRatio;
        }

        public void setLevelRatio(String levelRatio) {
            this.levelRatio = levelRatio;
        }
    }
}
