package com.example.JWT.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
