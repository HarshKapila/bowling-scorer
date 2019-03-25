package com.harshpal.scorer.bowling.game;

import static com.harshpal.scorer.bowling.scoring.ScoringServiceImpl.MAX_HIT;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Frame {
    private Integer first;
    private Integer second;
    private Integer extra;

    @JsonIgnore
    public boolean isStrike() {
        return first == MAX_HIT;
    }

    @JsonIgnore
    public boolean isSpare() {
        return first != null && second != null && (first + second >= MAX_HIT || second == MAX_HIT);
    }

    @JsonIgnore
    public boolean isFirstChance() {
        return first == null;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getExtra() {
        return extra;
    }

    public void setExtra(Integer extra) {
        this.extra = extra;
    }
}
