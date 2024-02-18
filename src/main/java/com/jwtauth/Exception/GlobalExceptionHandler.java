package com.jwtauth.Exception;

import com.jwtauth.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResouceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResouceNotFoundException ex){
        String msg=ex.getMessage();
        return  new ResponseEntity<>(new ApiResponse(msg,false), HttpStatus.BAD_REQUEST);
    }

}
