package com.hnieacm.media.Service.Impl.path;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.path.AllPathService;
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
public class AllPathServiceImpl implements AllPathService {

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
}
