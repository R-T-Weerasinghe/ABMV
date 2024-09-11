package com.app.environment;

import com.app.agent.Agent;
import com.app.message.Message;

import java.util.ArrayList;
import java.util.List;

public class Environment {
    private List<Agent> agents;
    private List<Message> messageQueue;

    public Environment() {
        agents = new ArrayList<>();
        messageQueue = new ArrayList<>();
    }
    public void addAgent(Agent agent) {
        agents.add(agent);
    }
    public void sendMessage(Message message) {
        messageQueue.add(message);
    }
    public void processMessages() {
        List<Message> toProcess = new ArrayList<>(messageQueue);
        messageQueue.clear();
        for (Message message : toProcess) {
            message.getReceiver().handleMessage(message);
        }
    }
    public List<Agent> getAgents() {
        return agents;
    }
}
