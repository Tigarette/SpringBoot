package com.hnieacm.media.Service.Impl.media;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.media.DownLoadService;
import com.hnieacm.media.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Service
public class DownLoadServiceImpl implements DownLoadService {
    public String download(HttpServletRequest request, HttpServletResponse response, String path, String medianame){

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        OutputStream outputStream=null;
        InputStream inputStream=null;
        BufferedInputStream bufferedInputStream=null;
        byte[] bytes=new byte[4096];
        try{
            response.setHeader("Content-Disposition", "attachment;filename=" +  URLEncoder.encode(medianame, "UTF-8"));
            response.setContentType("application/octet-stream;charset=utf-8");
            inputStream = new FileInputStream(path);
            bufferedInputStream = new BufferedInputStream(inputStream);
            outputStream = response.getOutputStream();
            int i = bufferedInputStream.read(bytes);
            while (i!=-1){
                outputStream.write(bytes,0,i);
                i = bufferedInputStream.read(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream!=null){
                    inputStream.close();
                }
                if (outputStream!=null){
                    outputStream.close();
                }
                if (bufferedInputStream!=null){
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
