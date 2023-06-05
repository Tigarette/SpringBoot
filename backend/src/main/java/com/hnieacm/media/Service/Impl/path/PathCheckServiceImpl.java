package com.hnieacm.media.Service.Impl.path;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.path.PathCheckService;
import com.hnieacm.media.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class PathCheckServiceImpl implements PathCheckService {

    @Override
    public Map<String, String> checkAll(String dir_name, String dir, String media_name) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        File file = new File("Z:/" + dir_name + "/" + dir + "/" + media_name);
        Map<String, String> map = new HashMap<>();
        if(!file.exists()){
            map.put("error_message", "fail");
            log.error("你小子乱搞？："+ user.getName());
        }else{
            map.put("error_message", "success");
        }
        return map;
    }

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
            log.error("你小子乱搞？："+ user.getName());
        }else{
            map.put("error_message", "success");
        }
        return map;
    }
}
