package com.hnieacm.media.Service.Impl.media;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.media.AddTimeService;
import com.hnieacm.media.dao.UserDao;
import com.hnieacm.media.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddTimeServiceImpl implements AddTimeService {

    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, String> addTime() {

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();

        Long process = user.getProcess();

        user.setProcess(process + 5);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", user.getId());

        userDao.update(user, queryWrapper);

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");

        return map;
    }
}
