package com.hnieacm.media.Service.history;

import java.util.Map;

public interface GetHistoryService {
    Map<String, Double> getHistory(String path);
}
