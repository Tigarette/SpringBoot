package com.hnieacm.media.controller.media;

import com.hnieacm.media.Service.media.GetMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetMediaController {

    @Autowired
    private GetMediaService GetMediaService;

    @PostMapping("/media/getTotal/")
    private Integer getTotal(){
        return GetMediaService.getTotal();
    }

    @GetMapping("/medialist/")
    public List<String> getList(@RequestParam Map<String, String> data){

        Integer page = Integer.parseInt(data.get("page"));

        return GetMediaService.getList(page);
    }

    @PostMapping("/GetMediaName/")
    public List<String> GetMediaName(@RequestParam Map<String, String> data){
        String path = data.get("path");
        Integer page = Integer.parseInt(data.get("page"));
        return GetMediaService.getMediaName(path, page);
    }

    @PostMapping("/getSumPage/")
    public Integer getSumPage(@RequestParam Map<String, String> data){
        String path = data.get("path");

        return GetMediaService.getSumPage(path);
    }

    @PostMapping("/getMediaList/")
    public List<String> getMediaList(@RequestParam Map<String, String> data){

        String path = data.get("path");

        return GetMediaService.getMediaList(path);
    }
}
