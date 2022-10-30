package com.hnieacm.media.controller.email;

import com.hnieacm.media.Service.email.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SendEmailController {

    @Autowired
    private SendEmailService sendEmailService;

    @PostMapping("/user/sendemail/")
    public Map<String, String> sendEmail(@RequestParam Map<String, String> data){
        String email = data.get("email");
        return sendEmailService.sendSimpleMail(email);
    }
}
