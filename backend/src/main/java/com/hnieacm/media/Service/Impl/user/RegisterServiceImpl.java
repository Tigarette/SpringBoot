package com.hnieacm.media.Service.Impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnieacm.media.Service.user.RegisterService;
import com.hnieacm.media.dao.UserDao;
import com.hnieacm.media.model.User;
import com.hnieacm.media.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String name, String password, String conPassword,
                                        String group, String email, String code) {

        Map<String, String> map = new HashMap<>();

        // 去除前后空格
        username = username.trim();
        password = password.trim();
        name = name.trim();

        List<User> lists1 = userDao.selectList(new QueryWrapper<User>().eq("mail", email));
        if(!lists1.isEmpty()){
            map.put("error_message", "该邮箱已被注册,可尝试找回密码");
            return map;
        }

        if(username.length() == 0){
            map.put("error_message", "用户名不得为空!");
            return map;
        }

        if(name.length() == 0){
            map.put("error_message", "真实姓名不得为空!");
            return map;
        }

        if(password.length() == 0 || conPassword.length() == 0){
            map.put("error_message", "密码不得为空!");
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

        if(password.length() > 30 || conPassword.length() > 30) {
            map.put("error_message", "密码长度过长!");
            return map;
        }

        if(!password.equals(conPassword)){
            map.put("error_message", "两次输入的密码不一致!");
            return map;
        }

        if(group.equals("default")){
            map.put("error_message", "未选择年级部!");
            return map;
        }

        if(password.length() < 6){
            map.put("error_message", "密码长度不得小于六位!");
            return map;
        }

        if(username.length() > 20){
            map.put("error_message", "用户名过长!");
            return map;
        }

        List<User> lists = userDao.selectList(new QueryWrapper<User>().eq("mail", email));
        if(!lists.isEmpty()){
            map.put("error_message", "该邮箱已被注册,可尝试找回密码");
            return map;
        }

        HttpSession session = httpServletRequest.getSession(false);
        if(session == null){
            map.put("error_message", "验证码已失效或未发送!");
            return map;
        }
        String code1 = (String)session.getAttribute("code");
        String email1 = (String)session.getAttribute("email");
//        Claims claims;
//        try {
//            System.out.println(jwtCode);
//            claims = JwtUtil.parseJWT(jwtCode);
//
//        } catch (Exception e) {
//            map.put("error_message", "验证码已失效或未发送!");
//            return map;
//        }
//        String code1 = (String) claims.get("code");
//        String email1 = claims.getSubject();
        if(!code1.equals(code)){
            map.put("error_message", "验证码错误!");
            return map;
        }
        if(!email1.equals(email)){
            map.put("error_message", "验证码与邮箱不匹配!");
            return map;
        }

        session.removeAttribute("email");
        session.removeAttribute("code");

        String photo = "http://q1.qlogo.cn/g?b=qq&nk=" + email + "&s=640";

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(null, username, name, encodedPassword, email, 0, group, new Date(), 2, true, photo);
        userDao.insert(user);

        map.put("error_message", "success");
        return map;
    }
}
