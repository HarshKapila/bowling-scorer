package com.harshpal.scorer.bowling.scoring;

import java.util.List;
import java.util.Optional;

import com.harshpal.scorer.bowling.game.Frame;
import com.harshpal.scorer.bowling.game.Game;
import com.harshpal.scorer.bowling.game.GameStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoringServiceImpl implements ScoringService {
    public static final int MAX_CHANCES = 20;
    public static final int MAX_HIT = 10;
    private static final int FOUL = -1;

    @Autowired
    private GameStore gameStore;

    @Override
    public Game createGame(String playerName) {
        return gameStore.createGame(playerName);
    }

    @Override
    public Game addScore(String gameId, Integer pinsHit) {
        Game game = gameStore.getGame(gameId).orElseThrow(
                () -> new GameNotFoundException("No game found with id " + gameId));
        synchronized (game) {
            //direct get on optional as we are sure game is present
            game = gameStore.getGame(gameId).get();
            addScore(game, pinsHit);
        }
        return game;
    }

    private void addScore(Game game, Integer pinsHit) {
        if (game.isComplete()) {
            throw new GameOverException("Game already over, Can't add score");
        }
        Frame currentFrame = game.getCurrentFrame();
        int maxHits = getMaxPossibleHits(currentFrame);
        if (pinsHit > maxHits || pinsHit < FOUL) {
            throw new PinLimitExceededException("Out of limits for this roll, min : " + FOUL + ", max: " + maxHits);
        }
        addScoreForPreviousStrikesOrSpares(pinsHit, game, currentFrame);
        if (currentFrame.isFirstChance()) {
            currentFrame.setFirst(pinsHit);
        } else if (currentFrame.getSecond() == null) {
            currentFrame.setSecond(pinsHit);
        }
        if (currentFrame.isStrike() && game.getRollCount() < MAX_CHANCES - 2) {
            game.incrementRollCount(2);
        } else {
            game.incrementRollCount(1);
        }
    }

    private void addScoreForPreviousStrikesOrSpares(Integer pinsHit, Game game, Frame currentFrame) {
        if (pinsHit < 0) {
            return;
        }
        Frame lastFrame;
        List<Frame> frames = game.getFrames();
        int rollCount = game.getRollCount();
        if (rollCount >= 2) {
            lastFrame = frames.get(rollCount / 2 - 1);
            if (lastFrame.isStrike() || (lastFrame.isSpare() && currentFrame.isFirstChance())) {
                lastFrame.setExtra(Optional.ofNullable(lastFrame.getExtra()).orElse(0) + pinsHit);
            }
            if (rollCount >= 4) {
                Frame secondLastFrame = frames.get(rollCount / 2 - 2);
                if (secondLastFrame.isStrike() && lastFrame.isStrike() && currentFrame.isFirstChance()) {
                    secondLastFrame.setExtra(secondLastFrame.getExtra() + pinsHit);
                }
            }
        }
        if (rollCount == MAX_CHANCES) {
            currentFrame.setExtra(pinsHit);
        }
    }

    private static int getMaxPossibleHits(Frame currentFrame) {
        if (currentFrame.isFirstChance() || currentFrame.isStrike() || currentFrame.isSpare()) {
            return MAX_HIT;
        } else {
            return MAX_HIT - currentFrame.getFirst();
        }
    }
}
