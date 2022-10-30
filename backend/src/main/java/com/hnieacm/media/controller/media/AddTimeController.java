package com.hnieacm.media.controller.media;


import com.hnieacm.media.Service.media.AddTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddTimeController {

    @Autowired
    private AddTimeService addTimeService;

    @PostMapping("/user/addTime/")
    public Map<String, String> addTime(){
        return addTimeService.addTime();
    }
}
