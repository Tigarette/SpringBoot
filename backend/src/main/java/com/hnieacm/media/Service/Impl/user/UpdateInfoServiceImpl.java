package com.hnieacm.media.Service.Impl.user;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.user.UpdateInfoService;
import com.hnieacm.media.dao.UserDao;
import com.hnieacm.media.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateInfoServiceImpl implements UpdateInfoService {

    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, String> updateInfo(String name, String represent, String group) {

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();

        if(represent.length() == 0){
            represent = "这个人很懒";
        }

        if(name.length() == 0){
            map.put("error_message", "姓名不得为空");
            return map;
        }

        if(name.length() > 10){
            map.put("error_message", "姓名长度不得超过10个字符");
            return map;
        }

        if(represent.length() > 20){
            map.put("error_message", "个人简介长度不得超过20个字符");
            return map;
        }


        user.setName(name);
        user.setRepresent(represent);
        user.setGrp(group);

        userDao.updateById(user);
        map.put("error_message", "success");

        return map;
    }
}
