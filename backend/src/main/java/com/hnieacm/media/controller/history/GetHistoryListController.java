package com.hnieacm.media.controller.history;

import com.alibaba.fastjson2.JSONObject;
import com.hnieacm.media.Service.history.GetHistoryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetHistoryListController {

    @Autowired
    private GetHistoryListService getHistoryListService;

    @PostMapping("/user/history/")
    public JSONObject getHistoryList(@RequestParam Map<String, String> data){
        Integer page = Integer.parseInt(data.get("page"));
        return getHistoryListService.getHistoryList(page);
    }

}
