package com.app.math;

import org.jetbrains.annotations.NotNull;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public void addInPlace(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public void multiplyInPlace(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public Vector2D divide(double scalar) {
        if (scalar != 0) {
            return new Vector2D(this.x / scalar, this.y / scalar);
        } else {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }

    public void divideInPlace(double scalar) {
        if (scalar != 0) {
            this.x /= scalar;
            this.y /= scalar;
        } else {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double magnitudeSquared() {
        return x * x + y * y;
    }

    public Vector2D normalize() {
        double mag = magnitude();
        if (mag != 0) {
            return new Vector2D(x / mag, y / mag);
        } else {
            return new Vector2D(0, 0);
        }
    }

    public void normalizeInPlace() {
        double mag = magnitude();
        if (mag != 0) {
            this.x /= mag;
            this.y /= mag;
        }
    }

    public Vector2D limit(double max) {
        if (magnitudeSquared() > max * max) {
            return this.normalize().multiply(max);
        }
        return new Vector2D(x, y);
    }

    public void limitInPlace(double max) {
        if (magnitudeSquared() > max * max) {
            normalizeInPlace();
            multiplyInPlace(max);
        }
    }

    public void setMagnitude(double mag) {
        normalizeInPlace();
        multiplyInPlace(mag);
    }

    public double distance(Vector2D other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    public Vector2D rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vector2D(x * cos - y * sin, x * sin + y * cos);
    }

    public static Vector2D random2D() {
        double angle = Math.random() * 2 * Math.PI;
        return new Vector2D(Math.cos(angle), Math.sin(angle));
    }

    @Override
    public String toString() {
        return "Vector2D(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector2D vector2D = (Vector2D) obj;
        return Double.compare(vector2D.x, x) == 0 &&
                Double.compare(vector2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(x) + Double.hashCode(y);
    }
}