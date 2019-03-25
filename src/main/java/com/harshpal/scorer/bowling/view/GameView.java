package com.harshpal.scorer.bowling.view;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harshpal.scorer.bowling.game.Game;

public class GameView {

    @JsonProperty
    private String gameId;

    @JsonProperty
    private String playerName;

    @JsonProperty
    private boolean complete;

    @JsonProperty
    private List<FrameView> frames;

    private GameView(String gameId, String playerName, boolean complete,
            List<FrameView> frames) {
        this.gameId = gameId;
        this.playerName = playerName;
        this.complete = complete;
        this.frames = frames;
    }

    public static GameView fromGame(Game game) {
        List<FrameView> frames = game.getFrames()
                .stream()
                .filter(Objects::nonNull)
                .map(FrameView::fromFrame)
                .collect(Collectors.toList());
        return new GameView(game.getId(), game.getPlayerName(), game.isComplete(), frames);
    }
}
