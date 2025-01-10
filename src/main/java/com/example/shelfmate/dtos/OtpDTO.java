package com.example.shelfmate.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OtpDTO {

    @NotBlank(message = "OTP is required")
    private String otp;

}
