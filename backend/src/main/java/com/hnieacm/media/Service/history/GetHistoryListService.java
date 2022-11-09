package com.hnieacm.media.Service.history;

import com.alibaba.fastjson2.JSONObject;

public interface GetHistoryListService {
    JSONObject getHistoryList(Integer page);
}
