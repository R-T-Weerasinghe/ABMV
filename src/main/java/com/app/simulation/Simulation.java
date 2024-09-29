package com.app.simulation;

import com.app.boid.Boid;
import com.app.math.Vector2D;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;

public class Simulation {
    private static final int INITIAL_BOID_COUNT = 1;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private List<Boid> boids;
    private ExecutorService executor;
    private volatile boolean running;
    private Random random;

    public Simulation() {
        this.boids = new CopyOnWriteArrayList<>();
        this.running = false;
        this.random = new Random();
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        initializeBoids();
    }

    private void initializeBoids() {
        for (int i = 0; i < INITIAL_BOID_COUNT; i++) {
            addRandomBoid();
        }
    }

    public void addRandomBoid() {
        double x = random.nextDouble() * WIDTH;
        double y = random.nextDouble() * HEIGHT;
        addBoid(new Boid(x, y));
    }

    public void addBoid(Boid boid) {
        boids.add(boid);
    }

    public void removeBoid(Boid boid) {
        boids.remove(boid);
    }

    public List<Boid> getBoids() {
        return boids;
    }

    public void start() {
        running = true;
        executor.execute(this::run);
    }

    public void stop() {
        running = false;
    }

    private void run() {
        while (running) {
            update();
            try {
                Thread.sleep(16); // Approximately 60 FPS
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void update() {
        // Update boids in parallel
        boids.parallelStream().forEach(boid -> boid.update(boids));

        // Optionally, you can add or remove boids dynamically here
        // For example, to maintain a constant number of boids:
        // if (boids.size() < INITIAL_BOID_COUNT) {
        //     addRandomBoid();
        // }
    }

    // Additional methods for manipulating the simulation state

    public void setBoidsSpeed(double speed) {
        for (Boid boid : boids) {
            boid.setMaxSpeed(speed);
        }
    }

    public void setPerceptionRadius(double radius) {
        for (Boid boid : boids) {
            boid.setPerceptionRadius(radius);
        }
    }

    public void setSeparationWeight(double weight) {
        for (Boid boid : boids) {
            boid.setSeparationWeight(weight);
        }
    }

    public void setAlignmentWeight(double weight) {
        for (Boid boid : boids) {
            boid.setAlignmentWeight(weight);
        }
    }

    public void setCohesionWeight(double weight) {
        for (Boid boid : boids) {
            boid.setCohesionWeight(weight);
        }
    }
//
//    // Method to add an obstacle or predator
//    public void addObstacle(Vector2D position, double radius) {
//        // Implementation depends on how you want to represent obstacles
//        // For simplicity, we could add a special type of "obstacle boid" to the boids list
//        Boid obstacle = new Boid(position.x, position.y);
//        obstacle.setObstacle(true);
//        obstacle.setObstacleRadius(radius);
//        addBoid(obstacle);
//    }

    // Method to clear all boids and reset the simulation
    public void reset() {
        boids.clear();
        initializeBoids();
    }
}