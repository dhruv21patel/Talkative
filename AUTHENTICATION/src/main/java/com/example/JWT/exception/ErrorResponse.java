package com.example.JWT.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private String details;
    private HttpStatus status;
}
