package com.borathings.borapagar.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Constrói a resposta da exceção lançada
     * @param ex - ApiException - Exceção lançada
     * @return ResponseEntity<Object> - Exceção serializada
     */
    private ResponseEntity<Object> buildResponseEntityFromException(ApiException ex) {
        return new ResponseEntity<>(ex, ex.getStatus());
    }

    /**
     * Trata exceções lançadas pela aplicação que não foram pegas
     * pelos outros handlers.
     * @param ex - Exception - Exceção lançada
     * @return ResponseEntity<Object> - Exceção serializada
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return buildResponseEntityFromException(apiException);
    }
}
