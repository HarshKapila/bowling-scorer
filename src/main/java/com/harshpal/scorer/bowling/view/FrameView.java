package com.harshpal.scorer.bowling.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harshpal.scorer.bowling.game.Frame;

public class FrameView {
    @JsonProperty
    private Integer first;
    @JsonProperty
    private Integer second;
    @JsonProperty
    private Integer extra;

    public FrameView(Integer first, Integer second, Integer extra) {
        this.first = first;
        this.second = second;
        this.extra = extra;
    }

    public static FrameView fromFrame(Frame frame) {
        return new FrameView(frame.getFirst(), frame.getSecond(), frame.getExtra());
    }
}
