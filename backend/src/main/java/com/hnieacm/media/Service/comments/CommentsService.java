package com.hnieacm.media.Service.comments;

import com.alibaba.fastjson2.JSONObject;
import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.model.User;

import java.util.Map;

public interface CommentsService {
    public Map<String, String> sendComments(String message);

    public JSONObject GetComments(Integer page);

    public Map<String, String> DeleteComments(Long Id, User user);
}
