package com.app.agent;

import com.app.environment.Environment;
import com.app.message.Message;

public abstract class Agent implements Runnable {
    protected String id;
    protected Environment env;
    protected boolean isRunning = true;
    public Agent(String id, Environment env) {
        this.id = id;
        this.env = env;
    }
    /** Update the agent's state */
    public abstract void update();

    public abstract void handleMessage(Message message);

    public void sendMessage(Agent receiver, String content) {
        Message message = new Message(this, receiver, content);
        env.sendMessage(message);
    }

    public String getId() {
        return id;
    }

    @Override
    public void run() {
        while(isRunning) {
            update();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        isRunning = false;
    }
}
