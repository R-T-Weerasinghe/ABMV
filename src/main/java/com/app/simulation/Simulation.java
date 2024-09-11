package com.app.simulation;

import com.app.agent.Agent;
import com.app.environment.Environment;

public class Simulation {
    private Environment env;
    private int stepCount = 0;
    private final int maxSteps = 100;

    public Simulation(Environment env) {
        this.env = env;
    }
    public void run() {
        while(stepCount < maxSteps) {
            System.out.println("Step: " + stepCount);
            for(Agent agent : env.getAgents()) {
                agent.update();
            }
            env.processMessages();
            stepCount++;
        }
        System.out.println("Simulation ended.");
    }
}
