package com.hnieacm.media.Service.media;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MediaPlayService {
    String video(HttpServletRequest request, HttpServletResponse response, String path);

}
