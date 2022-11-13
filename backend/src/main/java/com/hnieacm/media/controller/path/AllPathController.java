package com.hnieacm.media.controller.path;

import com.hnieacm.media.Service.path.AllPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AllPathController {

    @Autowired
    private AllPathService allPathService;

    @PostMapping("/check/allPath/")
    public Map<String, String> checkAllPath(@RequestParam Map<String, String> data){
        String dir_name = data.get("dir_name");
        String dir = data.get("dir");
        String media_name = data.get("media_name");

        return allPathService.checkAll(dir_name, dir, media_name);
    }
}
