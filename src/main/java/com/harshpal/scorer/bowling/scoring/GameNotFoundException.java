package com.harshpal.scorer.bowling.scoring;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends RuntimeException {

    GameNotFoundException(String message) {
        super(message);
    }
}
