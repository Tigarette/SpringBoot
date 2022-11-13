package com.hnieacm.media.Service.Impl.history;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.history.SetHistoryService;
import com.hnieacm.media.dao.HistoryDao;
import com.hnieacm.media.dao.UserDao;
import com.hnieacm.media.model.History;
import com.hnieacm.media.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SetHistoryServiceImpl implements SetHistoryService {

    @Autowired
    private HistoryDao historyDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, String> setHistory(Double time, String path) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();

        File file = new File("Z:/" + path);
        if(!file.exists()){
            map.put("error_message", "fail");
            return map;
        }


        QueryWrapper<History> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video", path).eq("userid", user.getId());

        List<History> list = historyDao.selectList(queryWrapper);

        History history = new History();
        history.setUserid(user.getId());
        history.setVideo(path);
        history.setProcess(time);
        history.setTime(new Date());
        history.setUsername(user.getUsername());

        if(list.isEmpty()){
            history.setId(null);
            historyDao.insert(history);
        }else{
            historyDao.update(history, queryWrapper);
        }

        map.put("error_message", "success");

        return map;
    }
}
