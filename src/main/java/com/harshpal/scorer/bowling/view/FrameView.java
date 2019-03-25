package com.harshpal.scorer.bowling.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harshpal.scorer.bowling.game.Frame;

class FrameView {
    @JsonProperty
    private Integer first;
    @JsonProperty
    private Integer second;
    @JsonProperty
    private Integer extra;

    private FrameView(Integer first, Integer second, Integer extra) {
        this.first = first;
        this.second = second;
        this.extra = extra;
    }

    static FrameView fromFrame(Frame frame) {
        return new FrameView(frame.getFirst(), frame.getSecond(), frame.getExtra());
    }
}
