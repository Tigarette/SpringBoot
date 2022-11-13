package com.hnieacm.media.Service.Impl.media;

import com.hnieacm.media.Service.media.MediaPlayService;
import com.hnieacm.media.config.NonStaticResourceHttpRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class MediaPlayServiceImpl  implements MediaPlayService {

    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    @Override
    public String video(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, path);
                nonStaticResourceHttpRequestHandler.handleRequest(request, response);
            }else{
                return "404";
            }
        } catch (Exception e) {
//                e.printStackTrace();
        }

        return path;
    }
}
