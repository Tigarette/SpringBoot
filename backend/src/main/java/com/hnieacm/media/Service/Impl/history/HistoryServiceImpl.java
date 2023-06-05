package com.hnieacm.media.Service.Impl.history;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.history.HistoryService;
import com.hnieacm.media.dao.HistoryDao;
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
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryDao historyDao;

    @Override
    public JSONObject getHistoryList(Integer page) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();

        IPage<History> historyPage = new Page<>(page, 10);
        QueryWrapper<History> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", user.getId()).orderByDesc("process");
        List<History> histories = historyDao.selectPage(historyPage, queryWrapper).getRecords();
        JSONObject resp = new JSONObject();

        resp.put("histories", histories);
        resp.put("histories_count", historyDao.selectCount(queryWrapper));


        return resp;
    }

    @Override
    public Map<String, Double> getHistory(String path) {

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();

        Map<String, Double> map = new HashMap<>();

        File file = new File("Z:/" + path);
        if(!file.exists()){
            map.put("error_message", (double) -1);
            return map;
        }

        QueryWrapper<History> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video", path).eq("userid", user.getId());

        List<History> list = historyDao.selectList(queryWrapper);

        if(list.isEmpty()){
            map.put("time", Double.parseDouble("0"));
        }else{
            map.put("time", list.get(0).getProcess());
        }
        return map;
    }

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
