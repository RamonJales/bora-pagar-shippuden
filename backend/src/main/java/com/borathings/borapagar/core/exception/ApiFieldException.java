package com.borathings.borapagar.core.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiFieldException extends ApiException {
    public ApiFieldException(HttpStatus status, Map<String, String> fieldErrors) {
        super(status);
        this.setMessage("One or more fields are invalid, check fieldErrors for more information");
        this.fieldErrors = fieldErrors;
    }

    Map<String, String> fieldErrors;
}
