package com.example.otp.service;

import com.example.otp.model.GoogleOtpData;

public interface GoogleOtpService {

    Boolean checkOtpNumber(String secret, Integer verificationCode);

    GoogleOtpData generateGoogleOtpData(String issuer, String accountName);
}
