package com.example.JWT.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {

    private String email;
    private String password;
}
