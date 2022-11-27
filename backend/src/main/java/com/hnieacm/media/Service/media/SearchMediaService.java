package com.hnieacm.media.Service.media;

import com.alibaba.fastjson2.JSONObject;


public interface SearchMediaService {
    JSONObject SearchMedia(String name, Integer page);
}
