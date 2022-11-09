package com.hnieacm.media.controller.user;

import com.hnieacm.media.Service.user.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChangePasswordController {

    @Autowired
    private ChangePasswordService changePasswordService;

    @PostMapping("/user/change/")
    public Map<String, String> changePsw(@RequestParam Map<String, String> data){
        String password = data.get("password");
        String oldPassword = data.get("oldPassword");
        String conformPassword = data.get("conformPassword");
        return changePasswordService.changePassword(oldPassword, password, conformPassword);
    }
}
