package com.harshpal.scorer.bowling.game;

import java.util.Optional;

import com.harshpal.scorer.bowling.game.Game;

public interface GameStore {

    Game createGame(String playerName);

    Optional<Game> getGame(String id);
}
