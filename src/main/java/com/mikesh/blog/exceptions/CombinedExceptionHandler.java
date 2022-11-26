package com.mikesh.blog.exceptions;

import com.mikesh.blog.payloads.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CombinedExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
        String errorMessage = exception.getMessage();
        return new ResponseEntity<APIResponse>(new APIResponse(errorMessage,false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((e) ->{
            String errorFieldName = ((FieldError)e).getField();
            String errorMessage = e.getDefaultMessage();
            errors.put(errorFieldName,errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> APIExceptionHandler(APIException exception){
        String errorMessage = exception.getMessage();
        return new ResponseEntity<APIResponse>(new APIResponse(errorMessage,false), HttpStatus.BAD_REQUEST);
    }
}
