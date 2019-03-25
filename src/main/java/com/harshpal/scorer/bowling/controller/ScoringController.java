package com.harshpal.scorer.bowling.controller;

import javax.validation.Valid;

import com.harshpal.scorer.bowling.scoring.ScoringService;
import com.harshpal.scorer.bowling.view.AddScoreRequest;
import com.harshpal.scorer.bowling.view.CreateGameRequest;
import com.harshpal.scorer.bowling.view.GameView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/game")
@RestController
public class ScoringController {

    @Autowired
    private ScoringService scoringService;

    @PostMapping("create")
    public GameView create(@Valid @RequestBody CreateGameRequest createGameRequest) {
        return GameView.fromGame(scoringService.createGame(createGameRequest.getPlayerName()));
    }

    @PatchMapping("score/add")
    public GameView addScore(@Valid @RequestBody AddScoreRequest addScoreRequest) {
        return GameView.fromGame(
                scoringService.addScore(addScoreRequest.getGameId(), addScoreRequest.getPinsHit()));
    }

}
