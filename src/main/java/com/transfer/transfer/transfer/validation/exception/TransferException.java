package com.transfer.transfer.transfer.validation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class TransferException extends ResponseStatusException {
    public TransferException(HttpStatus status, String reason) {
        super(status, reason);
        log.error(reason);
    }
}
