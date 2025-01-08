package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private List<Player> players = new ArrayList<>();

    @GetMapping("/game")
    public String game() {
        return "game";
    }

    @MessageMapping("/join")
    @SendTo("/topic/players")
    public List<Player> handleJoin(Player player) {
        players.add(player);
        return players;
    }

    @MessageMapping("/move")
    @SendTo("/topic/game")
    public GameState handleMove(PlayerMove move) {
        Player player = players.stream()
            .filter(p -> p.getName().equals(move.getPlayerName()))
            .findFirst()
            .orElse(null);
        
        if (player != null) {
            player.setX(move.getX());
            player.setY(move.getY());
        }
        
        return new GameState(players);
    }

    @MessageMapping("/shoot")
    @SendTo("/topic/game")
    public GameState handleShoot(PlayerShoot shoot) {
        // Créer un nouveau projectile
        Projectile projectile = new Projectile(
            shoot.getPlayerName(),
            shoot.getX(),
            shoot.getY(),
            shoot.getAngle()
        );
        
        return new GameState(players, projectile);
    }

    static class Player {
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

    static class PlayerMove {
        private String playerName;
        private double x;
        private double y;
        
        public String getPlayerName() { return playerName; }
        public void setPlayerName(String playerName) { this.playerName = playerName; }
        public double getX() { return x; }
        public void setX(double x) { this.x = x; }
        public double getY() { return y; }
        public void setY(double y) { this.y = y; }
    }

    static class PlayerShoot {
        private String playerName;
        private double x;
        private double y;
        private double angle;
        
        public String getPlayerName() { return playerName; }
        public void setPlayerName(String playerName) { this.playerName = playerName; }
        public double getX() { return x; }
        public void setX(double x) { this.x = x; }
        public double getY() { return y; }
        public void setY(double y) { this.y = y; }
        public double getAngle() { return angle; }
        public void setAngle(double angle) { this.angle = angle; }
    }

    static class Projectile {
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

    static class GameState {
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
} 