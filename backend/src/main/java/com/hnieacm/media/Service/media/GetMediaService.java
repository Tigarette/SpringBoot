package com.hnieacm.media.Service.media;

import java.util.List;

public interface GetMediaService {
    // 获取视频名称
    List<String> getMediaName(String path, Integer page);  // 获取次目录下所有视频

    Integer getTotal(); // 获取主目录个数

    List<String> getList(Integer page);  // 获取主目录

    Integer getSumPage(String path);  // 获取次目录下所有视频个数

    List<String> getMediaList(String path);  // 获取主目录下所有次目录
}
