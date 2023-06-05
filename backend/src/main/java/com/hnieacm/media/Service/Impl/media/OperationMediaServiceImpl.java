package com.hnieacm.media.Service.Impl.media;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.media.OperationMediaService;
import com.hnieacm.media.config.NonStaticResourceHttpRequestHandler;
import com.hnieacm.media.dao.MediaDao;
import com.hnieacm.media.model.Media;
import com.hnieacm.media.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Service
public class OperationMediaServiceImpl implements OperationMediaService {

    @Autowired
    private MediaDao mediaDao;

    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    @Override
    public String video(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, path);
                nonStaticResourceHttpRequestHandler.handleRequest(request, response);
            }else{
                return "404";
            }
        } catch (Exception e) {
//                e.printStackTrace();
        }

        return path;
    }

    public String download(HttpServletRequest request, HttpServletResponse response, String path, String medianame){

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        OutputStream outputStream=null;
        InputStream inputStream=null;
        BufferedInputStream bufferedInputStream=null;
        byte[] bytes=new byte[200000];
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

    @Override
    public JSONObject SearchMedia(String name, Integer page) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        JSONObject resp = new JSONObject();

        IPage<Media> mediaIPage = new Page<>(page, 10);

        QueryWrapper<Media> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<Media> list = mediaDao.selectPage(mediaIPage, queryWrapper).getRecords();
        resp.put("mediaList",list);
        resp.put("media_count", mediaDao.selectCount(queryWrapper));

        return resp;
    }
}
