package com.hnieacm.media.controller.media;


import com.hnieacm.media.Service.media.GetMediaDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetMediaDirController {

    @Autowired
    private GetMediaDirService getMediaDirService;

    @GetMapping("/medialist/")
    public List<String> getList(@RequestParam Map<String, String> data){

        Integer page = Integer.parseInt(data.get("page"));

        return getMediaDirService.getList(page);
    }
}
