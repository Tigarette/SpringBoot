package com.hnieacm.media.controller.media;

import com.alibaba.fastjson2.JSONObject;
import com.hnieacm.media.Service.media.OperationMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class OperationMediaController {

    @Autowired
    private OperationMediaService operationMediaService;

    @PostMapping("/search/")
    public JSONObject searchMedia(@RequestParam Map<String, String> data){
        String name = data.get("name");
        Integer page = Integer.valueOf(data.get("page"));

        return operationMediaService.SearchMedia(name, page);
    }

    @GetMapping("/media/video/{dirname}/{dir}/{medianame}")
    public String video(HttpServletRequest request, HttpServletResponse response, @PathVariable String dirname, @PathVariable String dir, @PathVariable String medianame){

        String path =  "Z:/" + dirname + "/" + dir + "/" + medianame;

        return operationMediaService.video(request, response, path);
    }

    @GetMapping("/download/{dirname}/{dir}/{medianame}")
    public String download(HttpServletRequest request, HttpServletResponse response, @PathVariable String dirname, @PathVariable String dir, @PathVariable String medianame){

        String path =  "Z:/" + dirname + "/" + dir + "/" + medianame;

        return operationMediaService.download(request, response, path, medianame);
    }
}
