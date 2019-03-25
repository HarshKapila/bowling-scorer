package com.harshpal.scorer.bowling.view;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddScoreRequest {

    @NotBlank
    private String gameId;

    @Min(-1)
    @Max(10)
    @NotNull
    private Integer pinsHit;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Integer getPinsHit() {
        return pinsHit;
    }

    public void setPinsHit(Integer pinsHit) {
        this.pinsHit = pinsHit;
    }
}