package com.app.math;

import org.jetbrains.annotations.NotNull;

public class Vector2D {
    private double x,y;

    // constructor, getters, setters
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double dx) {
        x = x + dx;
    }
    public void setY(double dy) {
        y = y + dy;
    }
    public void set(double dx, double dy) {
        x = x + dx;
        y = y + dy;
    }

    // Operations
    public Vector2D add(@NotNull Vector2D other) {
        return new Vector2D(x + other.getX(), y + other.getY());
    }

    public Vector2D subtract(@NotNull Vector2D other) {
        return new Vector2D(x - other.getX(), y - other.getY());
    }

    public Vector2D scale(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public double dotProduct(@NotNull Vector2D other) {
        return x * other.getX() + y * other.getY();
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D normalize() {
        double mag = magnitude();
        return new Vector2D(x / mag, y / mag);
    }

    public double angle(@NotNull Vector2D other) {
        return Math.acos(dotProduct(other) / (magnitude() * other.magnitude()));
    }

    public Vector2D rotate(double angle) {
        double newX = x * Math.cos(angle) - y * Math.sin(angle);
        double newY = x * Math.sin(angle) + y * Math.cos(angle);
        return new Vector2D(newX, newY);
    }

    public Vector2D project(@NotNull Vector2D other) {
        double scalar = dotProduct(other) / other.magnitude();
        return other.normalize().scale(scalar);
    }

    public Vector2D reflect(@NotNull Vector2D normal) {
        return project(normal).scale(2).subtract(this);
    }

    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
