package com.crazy4web.myapplication.ui.chat;

import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String messageUser;

    public String getMessageuserID() {
        return messageuserID;
    }

    public void setMessageuserID(String messageuserID) {
        this.messageuserID = messageuserID;
    }

    private String messageuserID;
    private long messageTime;

    public ChatMessage(String messageText, String messageUser, String messageUserID) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageuserID = messageUserID;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}