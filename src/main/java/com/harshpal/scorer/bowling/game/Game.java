package com.harshpal.scorer.bowling.game;

import static com.harshpal.scorer.bowling.scoring.ScoringServiceImpl.MAX_CHANCES;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Game {
    private String id;
    private String playerName;
    private int rollCount = 0;
    private List<Frame> frames = Arrays.asList(new Frame[10]);

    Game(String playerName) {
        this.playerName = playerName;
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Frame> getFrames() {
        return frames;
    }


    public Frame getCurrentFrame() {
        Frame frame = frames.get(rollCount == MAX_CHANCES ? rollCount / 2 - 1 : rollCount / 2);
        if (frame == null) {
            frame = new Frame();
            frames.set(rollCount / 2, frame);
        }
        return frame;
    }

    public void incrementRollCount(int rolls) {
        rollCount += rolls;
    }

    public int getRollCount() {
        return rollCount;
    }

    public boolean isComplete() {
        return rollCount > MAX_CHANCES || (rollCount == MAX_CHANCES && !frames.get(9).isSpare());
    }
}

