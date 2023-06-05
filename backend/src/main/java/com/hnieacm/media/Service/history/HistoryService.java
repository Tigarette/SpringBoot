package com.hnieacm.media.Service.history;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

public interface HistoryService {
    JSONObject getHistoryList(Integer page);

    Map<String, String> setHistory(Double time, String path);

    Map<String, Double> getHistory(String path);
}
