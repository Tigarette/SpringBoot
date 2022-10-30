package com.hnieacm.media.controller.media;


import com.hnieacm.media.Service.media.ChoiceMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChoiceMediaController {

    @Autowired
    private ChoiceMediaService choiceMediaService;

    @PostMapping("/getMediaList/")
    public List<String> getMediaList(@RequestParam Map<String, String> data){

        String path = data.get("path");

        return choiceMediaService.getMediaList(path);
    }
}
