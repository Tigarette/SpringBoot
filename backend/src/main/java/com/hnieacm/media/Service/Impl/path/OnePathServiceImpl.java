package com.hnieacm.media.Service.Impl.path;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.path.OnePathService;
import com.hnieacm.media.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class OnePathServiceImpl implements OnePathService {

    @Override
    public Map<String, String> checkOnePath(String dir_name) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();

        File dir = new File("Z:/" + dir_name);
        if(!dir.exists() && !dir.isDirectory()){
            map.put("error_message", "fail");
        }else{
            map.put("error_message", "success");
        }
        return map;
    }
}
