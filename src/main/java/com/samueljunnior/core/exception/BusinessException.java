package com.samueljunnior.core.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends ExceptionDefault {
    private String error;
    private HttpStatus statusCode;

    public BusinessException(String error){
        this.statusCode = HttpStatus.UNPROCESSABLE_ENTITY;
        this.error = error;
    }

    @Override
    public ProblemDetail toProblmeDetail() {
        final var problemDetail = ProblemDetail
                .forStatusAndDetail(this.statusCode, this.error);

        problemDetail.setTitle("It´s a business exception.");

        return problemDetail;
    }
}