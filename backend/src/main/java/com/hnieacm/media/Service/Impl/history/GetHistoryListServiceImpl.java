package com.hnieacm.media.Service.Impl.history;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.history.GetHistoryListService;
import com.hnieacm.media.dao.HistoryDao;
import com.hnieacm.media.model.History;
import com.hnieacm.media.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class GetHistoryListServiceImpl implements GetHistoryListService {

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
}
