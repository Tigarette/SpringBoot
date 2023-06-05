package com.hnieacm.media.Service.Impl.media;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.media.GetMediaService;
import com.hnieacm.media.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetMediaServiceImpl implements GetMediaService {

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

    @Override
    public Integer getTotal() {

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();

        String path = "Z:/";

        return new File(path).list().length;

    }

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

    @Override
    public List<String> getMediaList(String path) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();

        path = "Z:\\" + path;
        return List.of(new File(path).list());
    }

    @Override
    public Integer getSumPage(String path) {

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        path = "Z:\\" + path;
        Integer SumPage = new File(path).list().length;
        return SumPage;
    }
}
