package com.app.agent;

import com.app.agent.Agent;
import com.app.environment.Environment;
import com.app.message.Message;

public class SimpleAgent extends Agent {
    public SimpleAgent(String id, Environment env) {
        super(id, env);
    }

    @Override
    public void update() {
        for(Agent agent : env.getAgents()) {
            if(!agent.getId().equals(this.id)) {
                sendMessage(agent, "Hello from " + id);
            }
        }
    }

    @Override
    public void handleMessage(Message message) {
        System.out.println(id + " received: " + message.getContent());
    }
}
