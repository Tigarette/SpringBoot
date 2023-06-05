package com.hnieacm.media.Service.media;

import com.alibaba.fastjson2.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OperationMediaService {
    String video(HttpServletRequest request, HttpServletResponse response, String path);
    public String download(HttpServletRequest request, HttpServletResponse response, String path, String medianame);
    JSONObject SearchMedia(String name, Integer page);
}
