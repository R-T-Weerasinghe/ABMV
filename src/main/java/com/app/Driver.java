package com.app;

import com.app.agent.SimpleAgent;
import com.app.environment.Environment;
import com.app.simulation.Simulation;

public class Driver {
    public static void main(String[] args) {
        Environment environment = new Environment();

        SimpleAgent agent1 = new SimpleAgent("Agent1", environment);
        SimpleAgent agent2 = new SimpleAgent("Agent2", environment);
        SimpleAgent agent3 = new SimpleAgent("Agent3", environment);
        SimpleAgent agent4 = new SimpleAgent("Agent4", environment);

        environment.addAgent(agent1);
        environment.addAgent(agent2);
        environment.addAgent(agent3);
        environment.addAgent(agent4);

        Simulation simulation = new Simulation(environment);
        simulation.run();
    }
}
