package com.harshpal.scorer.bowling.view;

import javax.validation.constraints.NotBlank;

public class CreateGameRequest {
    @NotBlank
    private String playerName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}