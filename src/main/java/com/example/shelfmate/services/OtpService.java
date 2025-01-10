package com.example.shelfmate.services.impls;

import com.example.shelfmate.dtos.OtpDTO;
import com.example.shelfmate.entities.Otp;
import com.example.shelfmate.entities.User;

public interface OtpService {

    Otp generateAndSaveOtp(User user);

    boolean verifyOtp(OtpDTO otpVDTO, User user);

    boolean isOtpExpired(User user);

    void resendOtp(String username);
}
