package com.hnieacm.media.controller.path;

import com.hnieacm.media.Service.path.OnePathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OnePathController {

    @Autowired
    private OnePathService onePathService;

    @PostMapping("/check/onePath/")
    public Map<String, String> checkOnePath(@RequestParam Map<String, String> data){

        String dir_name = data.get("dir_name");

        return onePathService.checkOnePath(dir_name);
    }
}
