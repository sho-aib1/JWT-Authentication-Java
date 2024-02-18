package com.jwtauth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse extends Throwable {

    private String response;
    private boolean status;
}
