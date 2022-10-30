package com.hnieacm.media.controller.media;

import com.hnieacm.media.Service.media.GetSumPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetSumPageController {

    @Autowired
    private GetSumPageService getSumPageService;

    @PostMapping("/getSumPage/")
    public Integer getSumPage(@RequestParam Map<String, String> data){
        String path = data.get("path");

        return getSumPageService.getSumPage(path);
    }
}
