package com.Springboot.Connection.Controller;

import com.Springboot.Connection.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Autowired
    SmsService smsService;

    @GetMapping("/send-sms")
    public String sendSms(
            @RequestParam String phone,
            @RequestParam String message
    ) {
        return smsService.sendSms(phone, message);
    }
}
