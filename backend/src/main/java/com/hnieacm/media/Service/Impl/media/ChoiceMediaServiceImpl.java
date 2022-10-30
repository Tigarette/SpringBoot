package com.hnieacm.media.Service.Impl.media;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.media.ChoiceMediaService;
import com.hnieacm.media.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class ChoiceMediaServiceImpl implements ChoiceMediaService {
    @Override
    public List<String> getMediaList(String path) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();

        path = "Z:\\" + path;
        return List.of(new File(path).list());
    }
}
