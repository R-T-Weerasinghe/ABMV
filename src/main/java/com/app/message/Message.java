package com.app.message;

import com.app.agent.Agent;

public class Message {
    private Agent sender;
    private Agent receiver;
    private String content;

    public Message(Agent sender, Agent receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
    public Agent getSender() {
        return sender;
    }
    public Agent getReceiver() {
        return receiver;
    }
    public String getContent() {
        return content;
    }
}
