package com.example.test_chatgpt;

public class Message {
    public static String SENT_BY_ME = "me";
    public static String SENT_BY_BOT= "bot";

    String messsage;
    String sentBy;

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public Message(String messsage, String sentBy) {
        this.messsage = messsage;
        this.sentBy = sentBy;
    }
}
