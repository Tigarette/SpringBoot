package com.hnieacm.media.Service.Impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnieacm.media.Service.user.ForgetPasswordService;
import com.hnieacm.media.dao.UserDao;
import com.hnieacm.media.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Map<String, String> forgetPassword(String username, String password, String confirm, String email, String code) {

        Map<String, String> map = new HashMap<>();


        if(username.length() == 0){
            map.put("error_message", "用户名不得为空!");
            return map;
        }

        if(password.length() == 0 || confirm.length() == 0){
            map.put("error_message", "密码不得为空!");
            return map;
        }

        if(password.length() < 6 || confirm.length() < 6){
            map.put("error_message", "密码长度不得小于六位!");
            return map;
        }

        if(password.length() > 30 || confirm.length() > 30) {
            map.put("error_message", "密码长度过长!");
            return map;
        }

        if(email.length() == 0){
            map.put("error_message", "邮箱不得为空!");
            return map;
        }

        if(code.length() == 0){
            map.put("error_message", "验证码不得为空!");
            return map;
        }

        if(!password.equals(confirm)){
            map.put("error_message", "两次输入的密码不一致!");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> list = userDao.selectList(queryWrapper);

        if(list.isEmpty()){
            map.put("error_message", "此用户名不存在");
            return map;
        }

        if(!list.get(0).getMail().equals(email)){
            map.put("error_message", "该用户绑定的邮箱输入错误");
            return map;
        }


        HttpSession session = httpServletRequest.getSession(false);
        if(session == null){
            map.put("error_message", "验证码已失效或未发送!");
            return map;
        }
        String code1 = (String)session.getAttribute("code");
        String email1 = (String)session.getAttribute("email");
        if(!email1.equals(email)){
            map.put("error_message", "验证码与邮箱不匹配!");
            return map;
        }

        if(!code1.equals(code)){
            map.put("error_message", "验证码错误!");
            return map;
        }

        session.removeAttribute("email");
        session.removeAttribute("code");

        String encodedPassword = passwordEncoder.encode(password);
        User user = list.get(0);
        user.setPassword(encodedPassword);
        userDao.updateById(user);

        map.put("error_message", "success");
        return map;
    }
}
