package com.example.demo.game;

public class Projectile {
    private String playerName;
    private double x;
    private double y;
    private double angle;
    
    public Projectile(String playerName, double x, double y, double angle) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
    // Getters...
}