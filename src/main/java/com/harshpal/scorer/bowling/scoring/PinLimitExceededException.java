package com.harshpal.scorer.bowling.scoring;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PinLimitExceededException extends RuntimeException {
    PinLimitExceededException(String message) {
        super(message);
    }
}
