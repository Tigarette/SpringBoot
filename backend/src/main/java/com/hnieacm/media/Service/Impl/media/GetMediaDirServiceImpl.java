package com.hnieacm.media.Service.Impl.media;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.media.GetMediaDirService;
import com.hnieacm.media.model.User;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Service
public class GetMediaDirServiceImpl implements GetMediaDirService {

    @Override
    public List<String> getList(Integer page) {

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        String basePath = "Z:/";

        List<String> lists = List.of(new File(basePath).list());

        if(10 * page + 10 >= new File(basePath).list().length){
            return new ArrayList<>(lists.subList(10 * page, new File(basePath).list().length));
        }else {
            return new ArrayList<>(lists.subList(10 * page, 10 * page + 10));
        }
    }
}
