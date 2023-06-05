package com.hnieacm.media.Service.Impl.comments;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.comments.CommentsService;
import com.hnieacm.media.dao.CommentsDao;
import com.hnieacm.media.model.Comments;
import com.hnieacm.media.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsDao commentsDao;

    @Override
    public Map<String, String> sendComments(String message) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();

        if(message.length() == 0){
            map.put("error_message", "留言不得为空!");
            return map;
        }

        if(message.length() >= 255){
            map.put("error_message", "留言长度过长!");
            return map;
        }

        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", user.getId()).orderByDesc("time").last("limit 1");
        Comments comments1 = commentsDao.selectOne(queryWrapper);

        if(comments1 != null && new Date().getTime() - comments1.getTime().getTime() < 1000 * 60 * 5){

            map.put("error_message", "行不行啊细狗,太快了!");
            return map;
        }

        Comments comments = new Comments(null, new Date(), user.getId(), user.getName(), message, user.getPhoto());

        commentsDao.insert(comments);

        map.put("error_message", "success");
        return map;
    }

    @Override
    public JSONObject GetComments(Integer page){
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();

        IPage<Comments> commentsIPage = new Page<>(page, 10);
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("time");
        List<Comments> commentsList = commentsDao.selectPage(commentsIPage, queryWrapper).getRecords();
        JSONObject resp = new JSONObject();
        resp.put("comments", commentsList);
        resp.put("comments_count", commentsDao.selectCount(null));

        return resp;
    }

    @Override
    public Map<String, String> DeleteComments(Long Id, User user) {

        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", Id);
        Map<String, String> map = new HashMap<>();

        Comments comments = commentsDao.selectOne(queryWrapper);
        if(!Objects.equals(comments.getUserid(), user.getId()) && user.getAdmin() != 0){
            map.put("error_message", "你无权限删除!");
            return map;
        }

        commentsDao.delete(queryWrapper);

        map.put("error_message", "success");
        return map;

    }
}
