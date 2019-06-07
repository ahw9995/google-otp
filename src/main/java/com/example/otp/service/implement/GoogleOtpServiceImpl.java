package com.example.otp.service.implement;

import com.example.otp.model.GoogleOtpData;
import com.example.otp.service.GoogleOtpService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Slf4j
public class GoogleOtpServiceImpl implements GoogleOtpService {

    private final long addSecond = 30;

    @Override
    public Boolean checkOtpNumber(String secret, Integer verificationCode) {
        GoogleAuthenticator authenticator = new GoogleAuthenticator();
        return authenticator.authorize(secret, verificationCode, this.getTimeInMillis());
    }

    @Override
    public GoogleOtpData generateGoogleOtpData(String issuer, String accountName) {
        GoogleAuthenticator authenticator = new GoogleAuthenticator();
        GoogleAuthenticatorKey credentials = authenticator.createCredentials();
        String QRBarcodeUrl = this.generateQRBarcodeUrl(issuer, accountName, credentials);

        return GoogleOtpData.builder()
                .url(QRBarcodeUrl)
                .key(credentials.getKey())
                .build();

    }

    private String generateQRBarcodeUrl(String issuer, String accountName, GoogleAuthenticatorKey credentials) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(issuer, accountName, credentials);
    }

    private Long getTimeInMillis() {
        LocalDateTime now = LocalDateTime.now().plusSeconds(this.addSecond);
        return Timestamp.valueOf(now).getTime();
    }
}
