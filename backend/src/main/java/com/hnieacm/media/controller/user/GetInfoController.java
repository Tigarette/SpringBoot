package com.hnieacm.media.controller.user;


import com.hnieacm.media.Service.user.GetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetInfoController {

    @Autowired
    private GetInfoService getInfoService;

    @GetMapping("/user/info/")
    public Map<String, String> getInfo(){
        return getInfoService.getInfo();
    }
}
