package com.borathings.borapagar.core.exception;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Constrói a resposta da exceção lançada
     *
     * @param ex - ApiException - Exceção lançada
     * @return ResponseEntity<Object> - Exceção serializada
     */
    private ResponseEntity<Object> buildResponseEntityFromException(ApiException ex) {
        return new ResponseEntity<>(ex, ex.getStatus());
    }

    /**
     * Trata exceções lançadas pela aplicação que não foram pegas pelos outros handlers.
     *
     * @param ex - Exception - Exceção lançada
     * @return ResponseEntity<Object> - Exceção serializada
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        ApiException apiException =
                new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado", ex);
        return buildResponseEntityFromException(apiException);
    }

    /**
     * Trata exceções lançadas pela aplicação quando uma entidade não é encontrada.
     *
     * @param ex - EntityNotFoundException - Exceção lançada
     * @return ResponseEntity<Object> - Exceção serializada
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND, ex);
        return buildResponseEntityFromException(apiException);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestExcpetion(BadRequestException ex) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, ex);
        return buildResponseEntityFromException(apiException);
    }

    /**
     * Trata exceções lançadas pela aplicação quando uma entidade falha na validação. Este método
     * constrói uma instância da classe <code>ApiFieldException</code> extende a classe <code>
     * ApiException</code> com os erros de validação.
     *
     * <p>Os erros são representados pelo atributo fieldErrors, que é um mapa onde a chave é o nome
     * do campo que falhou na validação e o valor é um vetor contendo as mensagens de todos os erros
     * relacionados àquele campo.
     *
     * @param ex - MethodArgumentNotValidException - Exceção lançada
     * @param headers - HttpHeaders - Cabeçalhos da requisição que falhou na validação
     * @param status - HttpStatusCode - Status HTTP da resposta
     * @param request - WebRequest - Requisição que falhou na validação
     * @return ResponseEntity<ApiFieldException> - Instância de ApiFieldException serializada para
     *     JSON
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String, List<String>> fieldErrors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(
                        (error) -> {
                            String errorMessage = error.getDefaultMessage();

                            if (!fieldErrors.containsKey(error.getField())) {
                                fieldErrors.put(error.getField(), new ArrayList<>());
                            }
                            fieldErrors.get(error.getField()).add(errorMessage);
                        });
        ApiFieldException apiException = new ApiFieldException(HttpStatus.BAD_REQUEST, fieldErrors);
        return buildResponseEntityFromException(apiException);
    }
}
