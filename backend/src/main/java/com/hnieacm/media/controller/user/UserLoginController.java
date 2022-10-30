package com.hnieacm.media.controller.user;

import com.hnieacm.media.Service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserLoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login/")
    public Map<String, String> login(@RequestParam Map<String, String> map){
        String username = map.get("username");
        String password = map.get("password");
        return loginService.login(username, password);
    }
}
