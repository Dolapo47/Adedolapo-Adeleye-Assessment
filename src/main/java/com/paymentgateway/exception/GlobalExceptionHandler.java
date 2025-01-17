package com.paymentgateway.exception;

import com.paymentgateway.response.GatewayResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GatewayResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        String fieldName;
        String errorMessage;
        for(int i = 0; i < ex.getBindingResult().getAllErrors().size(); i++){
            fieldName = ((FieldError)ex.getBindingResult().getAllErrors().get(i)).getField();
            errorMessage = ex.getBindingResult().getAllErrors().get(i).getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        GatewayResponse gatewayResponse = GatewayResponse.builder()
                .code("90")
                .message("Validation Failed")
                .data(errors)
                .build();
        return new ResponseEntity<>(gatewayResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<GatewayResponse> handleDataIntegrityViolationException(MethodArgumentNotValidException ex) {

        GatewayResponse gatewayResponse = GatewayResponse.builder()
                .code("90")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(gatewayResponse, HttpStatus.BAD_REQUEST);
    }
}
