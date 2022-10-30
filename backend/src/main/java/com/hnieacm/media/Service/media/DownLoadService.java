package com.hnieacm.media.Service.media;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DownLoadService {
    public String download(HttpServletRequest request, HttpServletResponse response, String path, String medianame);
}
