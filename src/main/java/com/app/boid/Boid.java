package com.app.boid;

import com.app.math.Vector2D;

import java.util.List;

import java.util.Random;

public class Boid {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;

    private static final double MAX_FORCE = 0.03;
    private static double MAX_SPEED = 2;
    private static double PERCEPTION_RADIUS = 50;

    private static double SEPARATION_WEIGHT = 1.5;
    private static double ALIGNMENT_WEIGHT = 1.0;
    private static double COHESION_WEIGHT = 1.0;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final Random random = new Random();

    public Boid(double x, double y) {
        this.position = new Vector2D(x, y);
        this.velocity = new Vector2D(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1);
        this.acceleration = new Vector2D(0, 0);
    }

    public void update(List<Boid> boids) {
        Vector2D separation = separate(boids);
        Vector2D alignment = align(boids);
        Vector2D cohesion = cohere(boids);

        separation.multiply(SEPARATION_WEIGHT);
        alignment.multiply(ALIGNMENT_WEIGHT);
        cohesion.multiply(COHESION_WEIGHT);

        acceleration.add(separation);
        acceleration.add(alignment);
        acceleration.add(cohesion);

        velocity.addInPlace(acceleration);
        velocity.limitInPlace(MAX_SPEED);
        velocity.addInPlace(new Vector2D(random.nextDouble() * 2 - 1, random.nextDouble() * 2 - 1));
//        System.out.println("Boid: " + this.hashCode() + " Velocity: " + velocity.x + ", " + velocity.y);
        position.addInPlace(velocity);
//        acceleration.multiply(0); // Clear acceleration for next frame
//        System.out.println("Boid: " + this.hashCode() + " Position: " + position.x + ", " + position.y);

        wrapAround();
    }

    private Vector2D separate(List<Boid> boids) {
        Vector2D steering = new Vector2D(0, 0);
        int count = 0;
        for (Boid other : boids) {
            double distance = position.distance(other.position);
            if (other != this && distance < PERCEPTION_RADIUS) {
                Vector2D diff = position.subtract(other.position);
                diff.normalize();
                diff.multiply(1/distance); // Weight by distance
                steering.add(diff);
                count++;
            }
        }
        if (count > 0) {
            steering.multiply(1/count);
            steering.setMagnitude(MAX_SPEED);
            steering.subtract(velocity);
            steering.limit(MAX_FORCE);
        }
        return steering;
    }

    private Vector2D align(List<Boid> boids) {
        Vector2D steering = new Vector2D(0, 0);
        int count = 0;
        for (Boid other : boids) {
            double distance = position.distance(other.position);
            if (other != this && distance < PERCEPTION_RADIUS) {
                steering.add(other.velocity);
                count++;
            }
        }
        if (count > 0) {
            steering.divide(count);
            steering.setMagnitude(MAX_SPEED);
            steering.subtract(velocity);
            steering.limit(MAX_FORCE);
        }
        return steering;
    }

    private Vector2D cohere(List<Boid> boids) {
        Vector2D steering = new Vector2D(0, 0);
        int count = 0;
        for (Boid other : boids) {
            double distance = position.distance(other.position);
            if (other != this && distance < PERCEPTION_RADIUS) {
                steering.add(other.position);
                count++;
            }
        }
        if (count > 0) {
            steering.divide(count);
            steering.subtract(position);
            steering.setMagnitude(MAX_SPEED);
            steering.subtract(velocity);
            steering.limit(MAX_FORCE);
        }
        return steering;
    }

    private void wrapAround() {
        if (position.x < 0) position.x = WIDTH;
        if (position.y < 0) position.y = HEIGHT;
        if (position.x > WIDTH) position.x = 0;
        if (position.y > HEIGHT) position.y = 0;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setMaxSpeed(double speed) {
        MAX_SPEED = speed;
    }

    public void setPerceptionRadius(double radius) {
        PERCEPTION_RADIUS = radius;
    }

    public void setSeparationWeight(double weight) {
        SEPARATION_WEIGHT = weight;
    }

    public void setAlignmentWeight(double weight) {
         ALIGNMENT_WEIGHT = weight;
    }

    public void setCohesionWeight(double weight) {
        COHESION_WEIGHT = weight;
    }
}