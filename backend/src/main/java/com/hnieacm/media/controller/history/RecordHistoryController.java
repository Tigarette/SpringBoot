package com.hnieacm.media.controller.history;

import com.hnieacm.media.Service.history.SetHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RecordHistoryController {

    @Autowired
    private SetHistoryService setHistoryService;

    @PostMapping("/SetHistory/")
    public Map<String, String> setHistory(@RequestParam Map<String, String> data){
        Double time = Double.parseDouble(data.get("time"));
        String path = data.get("path");

        return setHistoryService.setHistory(time, path);
    }
}
