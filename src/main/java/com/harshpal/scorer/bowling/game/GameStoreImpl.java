package com.harshpal.scorer.bowling.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class GameStoreImpl implements GameStore {
    private static Map<String, Game> idToGame = new HashMap<>();

    @Override
    public Game createGame(String playerName) {
        Game game = new Game(playerName);
        idToGame.put(game.getId(), game);
        return game;
    }

    @Override
    public Optional<Game> getGame(String id) {
        return Optional.ofNullable(idToGame.get(id));
    }
}
