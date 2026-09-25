package com.seeshow.backend.dto.auth;


import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank
    @Size(max=50)
    private String firstName;

    @NotBlank
    @Size(max=50)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min=8, max=100)
    private String password;

    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String phoneNumber;
}
