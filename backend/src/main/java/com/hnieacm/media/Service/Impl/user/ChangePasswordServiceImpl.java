package com.hnieacm.media.Service.Impl.user;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.user.ChangePasswordService;
import com.hnieacm.media.dao.UserDao;
import com.hnieacm.media.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> changePassword(String oldPassword, String password, String conformPassword) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();
        Map<String, String> map = new HashMap<>();

        if(password.length() == 0){
            map.put("error_message", "密码不得为空");
            return map;
        }

        if(oldPassword.length() == 0){
            map.put("error_message", "旧密码不得为空");
            return map;
        }

        if(conformPassword.length() == 0){
            map.put("error_message", "确认密码不得为空");
            return map;
        }

        UsernamePasswordAuthenticationToken authenticationToken1 = new UsernamePasswordAuthenticationToken(user.getUsername(), oldPassword);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken1);  // 如果登陆失败回报异常
        UserDetailsImpl loginUser1 = (UserDetailsImpl) authenticate.getPrincipal();
        User user1 = loginUser1.getUser();

        if(conformPassword.length() > 30 || password.length() > 30 ) {
            map.put("error_message", "密码长度过长!");
            return map;
        }

        if(!password.equals(conformPassword)){
            map.put("error_message", "两次密码不相同");
            return map;
        }

        if(password.length() < 6){
            map.put("error_message", "密码长度不得小于6位");
            return map;
        }

        String encodedPassword  = passwordEncoder.encode(password);

        if(oldPassword.equals(password)){
            map.put("error_message", "新密码不得和旧密码相同");
            return map;
        }

        user1.setPassword(encodedPassword);
        userDao.updateById(user1);
        map.put("error_message", "success");
        return map;
    }
}
