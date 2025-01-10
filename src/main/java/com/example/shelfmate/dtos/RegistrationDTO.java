package com.example.shelfmate.dtos;

import lombok.Data;

@Data
public class RegistrationDTO {

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    private String lastName;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 9, message = "Username must be between 4 and 20 characters")
    @Pattern(regexp = "^[\\S]+$", message = "Username must not contain spaces")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
