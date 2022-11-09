package com.hnieacm.media.Service.Impl.email;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnieacm.media.Service.Impl.utils.HttpRestUtils;
import com.hnieacm.media.Service.email.SendEmailService;
import com.hnieacm.media.dao.UserDao;
import com.hnieacm.media.model.User;
import com.hnieacm.media.utils.JwtUtil;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserDao userDao;

    public static String creatCode() {
        String str="0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < 7;i ++) {
            int number=random.nextInt(9);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @Override
    public Map<String, String> sendSimpleMail(String email) {

        Map<String, String> map = new HashMap<>();

        if(email.length() == 0){
            map.put("error_message", "邮箱不得为空!");
            return map;
        }


        String code = creatCode();
        try {
            String url = "http://10.0.38.112/mail/sendEmail/";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            params.add("code", "tyy");
            params.add("content", "你的验证码为:" + code + ",有效时间为5分钟,请尽快使用!");
            params.add("email", email);
            String result = HttpRestUtils.post(url, params);
            Map<String, String> map1 = new ObjectMapper().readValue(result, new TypeReference<>() {});
            map.put("error_message", map1.get("msg"));
            if(map1.get("msg").equals("发送成功")){
                // session操作
//                JwtBuilder jwtBuilder = JwtUtil.creatJWT(email,5 * 60 * 1000L);
//                jwtBuilder.claim("code",code);
//                map.put("jwtCode",jwtBuilder.compact());
                HttpSession session = httpServletRequest.getSession();
                session.setAttribute("code", code);
                session.setAttribute("email", email);
                session.setMaxInactiveInterval(5 * 60);
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("error_message", "邮箱服务错误,请联系管理员处理");
        }
        return map;
    }
}
