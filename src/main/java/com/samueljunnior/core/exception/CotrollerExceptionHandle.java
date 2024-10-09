package com.samueljunnior.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CotrollerExceptionHandle {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e){
        log.error("HandleException:", e);
        return new ExceptionDefault().toProblmeDetail();
    }

    @ExceptionHandler(ExceptionDefault.class)
    public ProblemDetail handleBusinessException(ExceptionDefault be){
        return be.toProblmeDetail();
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ne){
        return ne.toProblmeDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        final var errorList = ex.getFieldErrors()
                .stream()
                .map(fieldError -> new InvalidField(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Fields errors");
        problemDetail.setProperty("invalid-fields", errorList);

        return problemDetail;
    }
}
