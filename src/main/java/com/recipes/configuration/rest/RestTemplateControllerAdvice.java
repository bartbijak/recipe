package com.recipes.configuration.rest;

import com.recipes.infrastructure.error.NotFoundException;
import com.recipes.infrastructure.error.UnexpectedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class RestTemplateControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public void handleNotFound(HttpServletResponse response) throws IOException {
        log.error("Resource not found");
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(UnexpectedException.class)
    public void handleUnexpectedException(HttpServletResponse response) throws IOException {
        log.error("Unexpected exception");
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}