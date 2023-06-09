package com.hnieacm.media.controller.history;

import com.alibaba.fastjson2.JSONObject;
import com.hnieacm.media.Service.history.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HistoryController {

    @Autowired
    private HistoryService HistoryService;

    @PostMapping("/GetHistory/")
    public Map<String, Double> getHistory(@RequestParam Map<String, String> data){
        String path = data.get("path");

        return HistoryService.getHistory(path);
    }

    @PostMapping("/SetHistory/")
    public Map<String, String> setHistory(@RequestParam Map<String, String> data){
        Double time = Double.parseDouble(data.get("time"));
        String path = data.get("path");

        return HistoryService.setHistory(time, path);
    }

    @PostMapping("/user/history/")
    public JSONObject getHistoryList(@RequestParam Map<String, String> data){
        Integer page = Integer.parseInt(data.get("page"));
        return HistoryService.getHistoryList(page);
    }
}
