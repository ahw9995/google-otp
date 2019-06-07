package com.example.otp.controller;

import com.example.otp.model.GoogleOtpData;
import com.example.otp.model.ResponseData;
import com.example.otp.service.GoogleOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleOtpController {

    private final GoogleOtpService googleOtpService;

    @Autowired
    public GoogleOtpController(GoogleOtpService googleOtpService) {
        this.googleOtpService = googleOtpService;
    }

    @GetMapping("/google-otp/generate-qr/issuer/{issuer}/account-name/{accountName}")
    public ResponseEntity<ResponseData> generateQRCode(
            @PathVariable(value = "issuer") String issuer, @PathVariable(value = "accountName") String accountName) {
        GoogleOtpData googleOtpData = this.googleOtpService.generateGoogleOtpData(issuer, accountName);
        return new ResponseEntity<>(new ResponseData<>(googleOtpData), HttpStatus.OK);
    }

    @GetMapping("/google-otp/check-otp/number/{number}/secret-code/{code}")
    public ResponseEntity<ResponseData> checkOtpNumber(
            @PathVariable(value = "number") Integer number, @PathVariable(value = "code") String code) {
        Boolean result = this.googleOtpService.checkOtpNumber(code, number);
        return new ResponseEntity<>(new ResponseData<>(result), HttpStatus.OK);
    }
}
