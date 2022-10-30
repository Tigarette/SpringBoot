package com.hnieacm.media.controller.history;

import com.hnieacm.media.Service.history.GetHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetHistoryController {

    @Autowired
    private GetHistoryService getHistoryService;

    @PostMapping("/GetHistory/")
    public Map<String, Double> getHistory(@RequestParam Map<String, String> data){
        String path = data.get("path");

        return getHistoryService.getHistory(path);
    }
}
