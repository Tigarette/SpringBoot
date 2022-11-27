package com.hnieacm.media.controller.media;

import com.alibaba.fastjson2.JSONObject;
import com.hnieacm.media.Service.media.SearchMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SearchMediaController {

    @Autowired
    private SearchMediaService searchMediaService;

    @PostMapping("/search/")
    public JSONObject searchMedia(@RequestParam Map<String, String> data){
        String name = data.get("name");
        Integer page = Integer.valueOf(data.get("page"));

        return searchMediaService.SearchMedia(name, page);
    }
}
