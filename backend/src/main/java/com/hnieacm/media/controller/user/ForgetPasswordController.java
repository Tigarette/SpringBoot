package com.hnieacm.media.controller.user;


import com.hnieacm.media.Service.user.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ForgetPasswordController {

    @Autowired
    private ForgetPasswordService forgetPasswordService;

    @PostMapping("/user/forget/")
    public Map<String, String> forgetPwd(@RequestParam Map<String, String> data){
        String username = data.get("username");
        String password = data.get("password");
        String confirm = data.get("confirm");
        String email = data.get("email");
        String code = data.get("code");

        return forgetPasswordService.forgetPassword(username, password, confirm, email, code);
    }
}
