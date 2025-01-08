package com.example.demo.game;

import java.util.List;

public class GameState {
    private List<Player> players;
    private Projectile lastProjectile;

    public GameState(List<Player> players) {
        this.players = players;
    }

    public GameState(List<Player> players, Projectile projectile) {
        this.players = players;
        this.lastProjectile = projectile;
    }
    // Getters...
}
