package com.borathings.borapagar.core.exception;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ApiException {
    private HttpStatus status;
    private String message;
    private String debugMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private ApiException() {
        timestamp = LocalDateTime.now(ZoneOffset.UTC);
    }

    public ApiException(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiException(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiException(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}