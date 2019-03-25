package com.harshpal.scorer.bowling.view;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.harshpal.scorer.bowling.game.Frame;
import com.harshpal.scorer.bowling.game.Game;

public class GameResponse {

    private String gameId;

    private String playerName;

    private boolean complete;

    private List<Frame> frames;

    public String getGameId() {
        return gameId;
    }

    private void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    private void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isComplete() {
        return complete;
    }

    private void setComplete(boolean complete) {
        this.complete = complete;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    private void setFrames(List<Frame> frames) {
        this.frames = frames;
    }

    public static GameResponse fromGame(Game game) {
        GameResponse response = new GameResponse();
        response.setGameId(game.getId());
        response.setComplete(game.isComplete());
        response.setPlayerName(game.getPlayerName());
        response.setFrames(game.getFrames()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
        );
        return response;
    }
}
