package com.hnieacm.media.controller.media;

import com.hnieacm.media.Service.media.MediaPlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MediaPlayController {

    @Autowired
    private MediaPlayService mediaPlayService;

    @GetMapping("/media/video/{dirname}/{dir}/{medianame}")
    public String video(HttpServletRequest request, HttpServletResponse response, @PathVariable String dirname,@PathVariable String dir,@PathVariable String medianame){

        String path =  "Z:/" + dirname + "/" + dir + "/" + medianame;

        return mediaPlayService.video(request, response, path);
    }
}
