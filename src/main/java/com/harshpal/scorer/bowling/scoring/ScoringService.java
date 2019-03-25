package com.harshpal.scorer.bowling.scoring;

import com.harshpal.scorer.bowling.game.Game;

public interface ScoringService {
    Game createGame(String playerName);

    Game addScore(String gameId, Integer pinsHit);

}
