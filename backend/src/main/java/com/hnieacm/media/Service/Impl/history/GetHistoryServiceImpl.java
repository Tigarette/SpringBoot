package com.hnieacm.media.Service.Impl.history;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.history.GetHistoryService;
import com.hnieacm.media.dao.HistoryDao;
import com.hnieacm.media.model.History;
import com.hnieacm.media.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetHistoryServiceImpl implements GetHistoryService {

    @Autowired
    private HistoryDao historyDao;

    @Override
    public Map<String, Double> getHistory(String path) {

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();

        Map<String, Double> map = new HashMap<>();

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
}
