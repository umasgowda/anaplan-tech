package com.anaplan.bringyourowninterview.dashboard.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class DashboardExceptionController {

    @ExceptionHandler(value = DashboardNotFoundException.class)
    public ResponseEntity<Object> exception(DashboardNotFoundException exception) {
        log.error("exception thrown", exception);
        return new ResponseEntity<>("Dashboard not found", HttpStatus.NOT_FOUND);
    }
}
