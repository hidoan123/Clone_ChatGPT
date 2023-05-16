package com.example.test_chatgpt.HistoryChat;

public class ChatOop {
    private int Id;
    private String requestChat;
    private String relyChat;

    public ChatOop(int id, String requestChat, String relyChat) {
        Id = id;
        this.requestChat = requestChat;
        this.relyChat = relyChat;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRequestChat() {
        return requestChat;
    }

    public void setRequestChat(String requestChat) {
        this.requestChat = requestChat;
    }

    public String getRelyChat() {
        return relyChat;
    }

    public void setRelyChat(String relyChat) {
        this.relyChat = relyChat;
    }
}
