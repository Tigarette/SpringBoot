package com.hnieacm.media.controller.media;

import com.hnieacm.media.Service.media.GetMediaNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetMediaNameController {

    @Autowired
    private GetMediaNameService getMediaNameService;

    @PostMapping("/GetMediaName/")
    public List<String> GetMediaName(@RequestParam Map<String, String> data){
        String path = data.get("path");
        Integer page = Integer.parseInt(data.get("page"));
        return getMediaNameService.getMediaName(path, page);
    }
}
