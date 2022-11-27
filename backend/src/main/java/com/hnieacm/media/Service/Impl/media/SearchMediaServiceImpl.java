package com.hnieacm.media.Service.Impl.media;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.media.SearchMediaService;
import com.hnieacm.media.dao.MediaDao;
import com.hnieacm.media.model.Media;
import com.hnieacm.media.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchMediaServiceImpl implements SearchMediaService {

    @Autowired
    private MediaDao mediaDao;

    @Override
    public JSONObject SearchMedia(String name, Integer page) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        JSONObject resp = new JSONObject();

        IPage<Media> mediaIPage = new Page<>(page, 10);

        QueryWrapper<Media> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<Media> list = mediaDao.selectPage(mediaIPage, queryWrapper).getRecords();
        resp.put("mediaList",list);
        resp.put("media_count", mediaDao.selectCount(queryWrapper));

        return resp;
    }
}
