package com.app.visualization;

import com.app.simulation.Simulation;
import com.app.boid.Boid;
import com.app.math.Vector2D;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.util.List;

public class Visualizer {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BOID_SIZE = 5;

    private Simulation simulation;
    private Canvas canvas;
    private GraphicsContext gc;

    public Visualizer(Simulation simulation, Stage primaryStage) {
        this.simulation = simulation;
        this.canvas = new Canvas(WIDTH, HEIGHT);
        this.gc = canvas.getGraphicsContext2D();
//
//        StackPane root = new StackPane(canvas);
//        Scene scene = new Scene(root, WIDTH, HEIGHT);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Flocking Simulation");
//        primaryStage.show();
//
//        // Add mouse click listener to add new boids
//        canvas.setOnMouseClicked(event -> {
//            simulation.addBoid(new Boid(event.getX(), event.getY()));
//        });
    }

    public void start() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw();
            }
        }.start();
    }

    private void draw() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        List<Boid> boids = simulation.getBoids();
        gc.setFill(Color.WHITE);

        for (Boid boid : boids) {
            drawBoid(boid);
        }

        // Display boid count
        gc.setFill(Color.YELLOW);
        gc.fillText("Boids: " + boids.size(), 10, 20);
    }

    private void drawBoid(Boid boid) {
        Vector2D position = boid.getPosition();
        Vector2D velocity = boid.getVelocity();

        // Save the current state
        gc.save();

        // Translate to the boid's position
        gc.translate(position.x, position.y);

        // Rotate in the direction of movement
        double angle = Math.atan2(velocity.y, velocity.x);
        gc.rotate(Math.toDegrees(angle));

        // Draw the boid as a triangle
        gc.setFill(Color.WHITE);
        gc.beginPath();
        gc.moveTo(BOID_SIZE * 2, 0);
        gc.lineTo(-BOID_SIZE, BOID_SIZE);
        gc.lineTo(-BOID_SIZE, -BOID_SIZE);
        gc.closePath();
        gc.fill();

        // Restore the state
        gc.restore();
    }

    public Canvas getCanvas() {
        return canvas;
    }

}