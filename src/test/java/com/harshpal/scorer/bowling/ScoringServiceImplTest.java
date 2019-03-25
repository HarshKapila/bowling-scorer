package com.harshpal.scorer.bowling;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.IntStream;

import com.harshpal.scorer.bowling.game.Frame;
import com.harshpal.scorer.bowling.game.Game;
import com.harshpal.scorer.bowling.scoring.GameNotFoundException;
import com.harshpal.scorer.bowling.scoring.GameOverException;
import com.harshpal.scorer.bowling.scoring.PinLimitExceededException;
import com.harshpal.scorer.bowling.scoring.ScoringService;
import com.harshpal.scorer.bowling.scoring.ScoringServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoringServiceImplTest {

    private static final String PLAYER_NAME = "player";

    @Autowired
    private ScoringService scoringService = new ScoringServiceImpl();


    @Test(expected = PinLimitExceededException.class)
    public void shouldValidateMaxAllowed() {
        //given
        Game game = scoringService.createGame(PLAYER_NAME);

        //when
        scoringService.addScore(game.getId(), 11);
    }

    @Test(expected = PinLimitExceededException.class)
    public void shouldValidateMaxAllowed_secondRollInFrame() {
        //given
        Game game = setupWithFirstRoll(5);

        //when
        scoringService.addScore(game.getId(), 6);
    }

    @Test(expected = PinLimitExceededException.class)
    public void shouldValidateMinAllowed() {
        //given
        Game game = scoringService.createGame(PLAYER_NAME);

        //when
        scoringService.addScore(game.getId(), -2);
    }

    @Test(expected = GameOverException.class)
    public void shouldNotAllowAddScoreToCompletedGame() {
        //given
        Game game = setupGameWithStrikes(12);

        //when
        scoringService.addScore(game.getId(), 10);
    }

    @Test(expected = GameNotFoundException.class)
    public void shouldRejectUnknownGames() {

        //when
        scoringService.addScore("random", 10);
    }

    @Test
    public void shouldCorrectlyCalculateBonusForStrike() {
        //given
        Game game = setupWithFirstRoll(10);

        //when
        scoringService.addScore(game.getId(), 10);
        scoringService.addScore(game.getId(), 5);
        game = scoringService.addScore(game.getId(), 3);

        //then
        Frame first = game.getFrames().get(0);
        Frame second = game.getFrames().get(1);
        assertThat(first.getExtra()).isEqualTo(15);
        assertThat(second.getExtra()).isEqualTo(8);
    }

    @Test
    public void perfectGame() {
        //given
        Game game = setupGameWithStrikes(12);

        //then
        assertThat(game.isComplete()).isTrue();
        Integer totalScore = game.getFrames()
                .stream()
                .mapToInt(frame -> frame.getFirst() + Optional.ofNullable(frame.getSecond()).orElse(0) + frame
                        .getExtra())
                .sum();
        assertThat(totalScore).isEqualTo(300);
    }


    @Test
    public void shouldAllowThreeRollsInLastFrame_ifStrikeOrSpare() {
        //given game with 9 strikes already played
        Game game = setupGameWithStrikes(9);

        //when
        scoringService.addScore(game.getId(), 7);
        //spare
        scoringService.addScore(game.getId(), 3);
        //third roll of last frame
        game = scoringService.addScore(game.getId(), 10);

        assertThat(game.isComplete()).isTrue();
        assertThat(game.getFrames().get(9).getExtra()).isEqualTo(10);

    }


    private Game setupWithFirstRoll(Integer firstHits) {
        Game game = scoringService.createGame(PLAYER_NAME);
        scoringService.addScore(game.getId(), firstHits);
        return game;
    }

    private Game setupGameWithStrikes(int numberOfStrikes) {
        Game game = scoringService.createGame(PLAYER_NAME);
        IntStream.range(0, numberOfStrikes).forEach(useless -> scoringService.addScore(game.getId(), 10));
        return game;
    }
}
