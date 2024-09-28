package com.app.agent;

import com.app.environment.Environment;
import com.app.message.Message;

public class MovingAgent extends Agent {
    private int x,y;
    private int speed = 1;
    private int interactionRadius = 10;

    public MovingAgent(String id, Environment env, int x, int y) {
        super(id, env);
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        for (Agent agent : env.getAgents()) {
            if (!agent.getId().equals(this.id)) {
                if (isClose(agent)) {
                    moveAway();
                } else {
                    moveTowards(agent);
                }
            }
        }
    }

    private boolean isClose(Agent agent) {
        MovingAgent otherAgent = (MovingAgent) agent;
        int distance = (int) Math.sqrt(Math.pow(otherAgent.x - this.x, 2) + Math.pow(otherAgent.y - this.y, 2));
        return distance < interactionRadius;
    }

    private void moveTowards(Agent agent) {
        MovingAgent otherAgent = (MovingAgent) agent;
        if (this.x < otherAgent.x) x += speed;
        if (this.x > otherAgent.x) x -= speed;
        if (this.y < otherAgent.y) y += speed;
        if (this.y > otherAgent.y) y -= speed;
    }

    private void moveAway() {
        // Simple random movement to simulate moving away
        x += (int) (Math.random() * 2 * speed - speed);
        y += (int) (Math.random() * 2 * speed - speed);
    }

    @Override
    public void handleMessage(Message message) {
        // No specific message handling for this case
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
