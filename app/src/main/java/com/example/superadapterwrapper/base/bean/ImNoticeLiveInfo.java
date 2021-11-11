package com.example.superadapterwrapper.base.bean;

import java.io.Serializable;

/**
 * ClassName ImNoticeLiveInfo
 * User: zuoweichen
 * Date: 2021/10/9 17:32
 * Description: 描述
 */
public class ImNoticeLiveInfo implements Serializable {


    private String content;
    private int msgType;
    private String resource;
    private String sendName;
    private VideoBean video;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }


    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }


    public static class VideoBean implements Serializable {
        private String action;
        private int client;
        private String mettingEndTime;
        private String mettingId;
        private String mettingStartTime;
        private String presenter;
        private String presenterName;
        private String roomNo;
        private int status;
        private String subject;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getClient() {
            return client;
        }

        public void setClient(int client) {
            this.client = client;
        }

        public String getMettingEndTime() {
            return mettingEndTime;
        }

        public void setMettingEndTime(String mettingEndTime) {
            this.mettingEndTime = mettingEndTime;
        }

        public String getMettingId() {
            return mettingId;
        }

        public void setMettingId(String mettingId) {
            this.mettingId = mettingId;
        }

        public String getMettingStartTime() {
            return mettingStartTime;
        }

        public void setMettingStartTime(String mettingStartTime) {
            this.mettingStartTime = mettingStartTime;
        }

        public String getPresenter() {
            return presenter;
        }

        public void setPresenter(String presenter) {
            this.presenter = presenter;
        }

        public String getPresenterName() {
            return presenterName;
        }

        public void setPresenterName(String presenterName) {
            this.presenterName = presenterName;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public void setRoomNo(String roomNo) {
            this.roomNo = roomNo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }
}
