package com.app;

import com.app.simulation.Simulation;
import com.app.visualization.Visualizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class FlockingSimulatorApp extends Application {
    private Simulation simulation;
    private Visualizer visualizer;

    @Override
    public void start(Stage primaryStage) {
        simulation = new Simulation();
        visualizer = new Visualizer(simulation, primaryStage);

        // Create control panel
        VBox controlPanel = createControlPanel();

        // Create main layout
        HBox mainLayout = new HBox(10);
        mainLayout.getChildren().addAll(visualizer.getCanvas(), controlPanel);

        // Set up the main scene
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Flocking Simulation");
        primaryStage.show();

        // Start the simulation and visualization
        simulation.start();
        visualizer.start();
    }

    private VBox createControlPanel() {
        VBox controlPanel = new VBox(10);
        controlPanel.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");

        // Add Boid button
        Button addBoidButton = new Button("Add Boid");
        addBoidButton.setOnAction(e -> simulation.addRandomBoid());

        // Speed slider
        Label speedLabel = new Label("Speed: 2.00");
        Slider speedSlider = createSlider(0, 5, 2, value -> {
            simulation.setBoidsSpeed(value);
            speedLabel.setText(String.format("Speed: %.2f", value));
        });

        // Perception radius slider
        Label perceptionLabel = new Label("Perception Radius: 50.00");
        Slider perceptionSlider = createSlider(10, 100, 50, value -> {
            simulation.setPerceptionRadius(value);
            perceptionLabel.setText(String.format("Perception Radius: %.2f", value));
        });

        // Separation weight slider
        Label separationLabel = new Label("Separation: 1.50");
        Slider separationSlider = createSlider(0, 3, 1.5, value -> {
            simulation.setSeparationWeight(value);
            separationLabel.setText(String.format("Separation: %.2f", value));
        });

        // Alignment weight slider
        Label alignmentLabel = new Label("Alignment: 1.00");
        Slider alignmentSlider = createSlider(0, 3, 1, value -> {
            simulation.setAlignmentWeight(value);
            alignmentLabel.setText(String.format("Alignment: %.2f", value));
        });

        // Cohesion weight slider
        Label cohesionLabel = new Label("Cohesion: 1.00");
        Slider cohesionSlider = createSlider(0, 3, 1, value -> {
            simulation.setCohesionWeight(value);
            cohesionLabel.setText(String.format("Cohesion: %.2f", value));
        });

        // Reset button
        Button resetButton = new Button("Reset Simulation");
        resetButton.setOnAction(e -> simulation.reset());

        controlPanel.getChildren().addAll(
                addBoidButton,
                new Label("Adjust Parameters:"),
                speedLabel, speedSlider,
                perceptionLabel, perceptionSlider,
                separationLabel, separationSlider,
                alignmentLabel, alignmentSlider,
                cohesionLabel, cohesionSlider,
                resetButton
        );

        return controlPanel;
    }

    private Slider createSlider(double min, double max, double initial, SliderChangeListener listener) {
        Slider slider = new Slider(min, max, initial);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();
            listener.onChanged(value);
        });

        return slider;
    }

    @FunctionalInterface
    interface SliderChangeListener {
        void onChanged(double newValue);
    }

    @Override
    public void stop() {
        simulation.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}