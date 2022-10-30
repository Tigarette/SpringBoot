package com.hnieacm.media.Service.Impl.media;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.media.GetSumPageService;
import com.hnieacm.media.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class GetSumPageServiceImpl implements GetSumPageService {

    @Override
    public Integer getSumPage(String path) {

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        path = "Z:\\" + path;
        Integer SumPage = new File(path).list().length / 20 + 1;
        return SumPage;
    }
}
