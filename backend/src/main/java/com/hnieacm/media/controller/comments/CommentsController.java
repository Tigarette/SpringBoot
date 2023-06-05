package com.hnieacm.media.controller.comments;

import com.alibaba.fastjson2.JSONObject;
import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.comments.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class CommentsController {


    @Autowired
    private CommentsService commentsService;

    @PostMapping("/get/")
    public JSONObject getComments(@RequestParam Map<String, String> data){
        Integer page = Integer.valueOf(data.get("page"));

        return commentsService.GetComments(page);
    }

    @PostMapping("/send/")
    public Map<String, String> sendComments(@RequestParam Map<String, String> data){
        String message = data.get("message");

        return commentsService.sendComments(message);
    }

    @PostMapping("/delete/")
    public Map<String, String> deleteComments(@RequestParam Map<String, String> data, Authentication authentication){
        if (authentication == null){
            Map<String,String> map = new HashMap<>();
            map.put("error_message", "未登录");
            return map;
        }
        Long messageId = Long.valueOf(data.get("messageId"));

        return commentsService.DeleteComments(messageId, ((UserDetailsImpl)authentication.getPrincipal()).getUser());
    }

}
