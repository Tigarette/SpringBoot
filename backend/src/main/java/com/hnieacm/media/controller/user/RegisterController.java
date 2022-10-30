package com.hnieacm.media.controller.user;

import com.hnieacm.media.Service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/user/register/")
    public Map<String, String> register(@RequestParam Map<String, String> data){
        String username = data.get("username");
        String name = data.get("name");
        String password = data.get("password");
        String conPassword = data.get("conPassword");
        String email = data.get("email");
        String code = data.get("code");
        String group = data.get("group");
        return registerService.register(username, name, password, conPassword, group, email, code);
    }
}
