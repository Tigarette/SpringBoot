package com.hnieacm.media.controller.ranklist;


import com.alibaba.fastjson2.JSONObject;
import com.hnieacm.media.Service.ranklist.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RankListController {

    @Autowired
    private RankListService rankListService;

    @PostMapping("/ranklist/")
    JSONObject getList(@RequestParam Map<String, String> data){
        Integer page = Integer.parseInt(data.get("page"));

        return rankListService.getList(page);
    }
}
