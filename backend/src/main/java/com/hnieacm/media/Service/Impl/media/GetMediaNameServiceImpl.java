package com.hnieacm.media.Service.Impl.media;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.media.GetMediaNameService;
import com.hnieacm.media.model.User;

import java.io.File;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetMediaNameServiceImpl implements GetMediaNameService {

    @Override
    public List<String> getMediaName(String path, Integer page) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        path = "Z:\\" + path;

        List<String> lists = new ArrayList<>();
        lists = List.of(new File(path).list());

        if(10 * page + 10 >= new File(path).list().length){
            return new ArrayList<>(lists.subList(10 * page, new File(path).list().length));
        }else {
            return new ArrayList<>(lists.subList(10 * page, 10 * page + 10));
        }
    }
}
