package com.hnieacm.media.Service.path;

import java.util.Map;

public interface PathCheckService {
    Map<String, String> checkAll(String dir_name, String dir, String media_name);
    Map<String, String> checkOnePath(String dir_name);
}
