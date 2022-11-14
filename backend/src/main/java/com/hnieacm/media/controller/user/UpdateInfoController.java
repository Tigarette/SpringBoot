package com.hnieacm.media.controller.user;

import com.hnieacm.media.Service.user.UpdateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateInfoController {

    @Autowired
    private UpdateInfoService updateInfoService;

    @PostMapping("/user/update/")
    public Map<String, String> updateInfo(@RequestParam Map<String, String> data){

        String name = data.get("name");
        String represent = data.get("represent");
        String group = data.get("group");

        return updateInfoService.updateInfo(name, represent, group);
    }

}
