package com.example.demo.game;

public class Player {
    private String name;
    private double x = 0;
    private double y = 0;
    private int health = 100;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
}