package com.samueljunnior.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ExceptionDefault extends RuntimeException {
    public ProblemDetail toProblmeDetail(){
        final var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal server error.");

        return problemDetail;
    }

}