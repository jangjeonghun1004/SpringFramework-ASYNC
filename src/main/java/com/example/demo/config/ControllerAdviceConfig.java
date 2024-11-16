package com.example.demo.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

@ControllerAdvice
public class ControllerAdviceConfig {

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public String handleAsyncTimeout(AsyncRequestTimeoutException ex) {
        return "/exception/asyncTimeoutException";
    }

}
