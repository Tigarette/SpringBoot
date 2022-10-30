package com.hnieacm.media.Service.Impl.user;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.user.LoginService;
import com.hnieacm.media.model.User;
import com.hnieacm.media.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> login(String username, String password) {

        Map<String, String> map = new HashMap<>();

        if(username.equals("")){
            map.put("error_message", "username");
            return map;
        }

        if(password.equals("")){
            map.put("error_message", "password");
            return map;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);  // 如果登陆失败回报异常
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticate.getPrincipal();
        User user = loginUser.getUser();

        String jwt = JwtUtil.createJWT(user.getId().toString());


        map.put("jwt_token", jwt);
        map.put("error_message", "success");
        return map;
    }
}
