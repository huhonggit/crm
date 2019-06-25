package com.crm.netty.chat;

/**
 * @author huhong
 * @date 2019-06-25 11:36
 */
public class ChatObject {

    private String userId;
    private String userName;
    private String message;

    public ChatObject() {
    }

    public ChatObject(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
