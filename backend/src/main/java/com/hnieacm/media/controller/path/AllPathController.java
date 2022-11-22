package com.hnieacm.media.controller.path;

import com.hnieacm.media.Service.path.AllPathService;
import com.hnieacm.media.Service.path.OnePathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/check")
public class AllPathController {

    @Autowired
    private AllPathService allPathService;

    @Autowired
    private OnePathService onePathService;

    @PostMapping("/allPath/")
    public Map<String, String> checkAllPath(@RequestParam Map<String, String> data){
        String dir_name = data.get("dir_name");
        String dir = data.get("dir");
        String media_name = data.get("media_name");

        return allPathService.checkAll(dir_name, dir, media_name);
    }

    @PostMapping("/onePath/")
    public Map<String, String> checkOnePath(@RequestParam Map<String, String> data){

        String dir_name = data.get("dir_name");

        return onePathService.checkOnePath(dir_name);
    }
}
