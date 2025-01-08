package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.game.GameState;
import com.example.demo.game.Player;
import com.example.demo.game.PlayerMove;
import com.example.demo.game.PlayerShoot;
import com.example.demo.game.Projectile;
import com.example.demo.game.Room;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class GameController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private List<Player> players = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();

    @PostMapping("/createRoom")
    public ResponseEntity<String> createRoom() throws JsonProcessingException {
        Room newRoom = new Room();
        newRoom.setCodeRoom(UUID.randomUUID().toString());
        newRoom.setPlayers(new ArrayList<>());
        rooms.add(newRoom);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(newRoom);

        return ResponseEntity.ok(jsonResponse);
    }

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
                shoot.getAngle());

        return new GameState(players, projectile);
    }

}