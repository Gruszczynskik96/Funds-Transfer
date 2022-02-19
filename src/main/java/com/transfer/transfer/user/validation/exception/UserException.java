package com.transfer.transfer.user.validation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class UserException extends ResponseStatusException {
    public UserException(HttpStatus status, String reason) {
        super(status, reason);
        log.error(reason);
    }
}
