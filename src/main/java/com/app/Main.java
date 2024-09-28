package com.app;

import com.app.agent.Agent;
import com.app.agent.MovingAgent;
import com.app.environment.Environment;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Canvas canvas;
    private Environment environment;

    public Main(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Start animation
        new Thread(() -> {
            while (true) {
                gc.clearRect(0, 0, 400, 400); // Clear canvas
                drawAgents(gc);
                try {
                    Thread.sleep(100); // Adjust the rendering speed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void drawAgents(GraphicsContext gc) {
        for (Agent agent : environment.getAgents()) {
            MovingAgent ma = (MovingAgent) agent;
            gc.fillOval(ma.getX(), ma.getY(), 5, 5); // Draw agents as small circles
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
