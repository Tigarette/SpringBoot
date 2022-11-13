package com.hnieacm.media.controller.media;

import com.hnieacm.media.Service.media.GetMediaDirTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetMediaDirTotalController {

    @Autowired
    private GetMediaDirTotalService getMediaDirTotalService;

    @PostMapping("/media/getTotal/")
    private Integer getTotal(){
        return getMediaDirTotalService.getTotal();
    }
}
