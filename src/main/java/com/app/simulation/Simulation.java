package com.app.simulation;

import com.app.agent.Agent;
import com.app.environment.Environment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulation {
    private Environment env;
    private ExecutorService executer;
    private int stepCount = 0;
    private final int maxSteps = 100;

    public Simulation(Environment env) {

        this.env = env;
        this.executer = Executors.newFixedThreadPool(env.getAgents().size());
    }
    public void run() {
        for (Agent agent : env.getAgents()) {
            executer.execute(agent);
        }
        try {
            Thread.sleep(10000); // Simulate for 10 seconds (adjust as needed)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Agent agent : env.getAgents()) {
            agent.stop();
        }

        executer.shutdown();
        System.out.println("Simulation ended.");
    }
}
