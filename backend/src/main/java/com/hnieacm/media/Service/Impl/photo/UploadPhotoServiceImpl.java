package com.hnieacm.media.Service.Impl.photo;

import com.hnieacm.media.Service.Impl.utils.UserDetailsImpl;
import com.hnieacm.media.Service.photo.UploadPhotoService;
import com.hnieacm.media.dao.UserDao;
import com.hnieacm.media.model.User;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Log4j2
public class UploadPhotoServiceImpl implements UploadPhotoService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private COSClient cosclient;

    public static final long serialVersionUID = 1L;

    @Override
    public Map<String, String> uploadPhoto(MultipartFile file) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String ImagePath = "https://tyy-1308175962.cos.ap-guangzhou.myqcloud.com/";
        String dir_name="image";
        String bucketName="tyy-1308175962"; //存储桶名称

        Map<String, String> map = new HashMap<>();
        if(file.getSize() > 5242880){
            map.put("error_message", "文件大小不得超过5M");
            return map;
        }

        String oldFileName = file.getOriginalFilename();
        String eName = oldFileName.substring(oldFileName.lastIndexOf(".")); //获取文件后缀
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + eName; //文件名

        String imagePath = user.getPhoto();

        imagePath = imagePath.split("/")[3] + "/" + imagePath.split("/")[4];
        if(cosclient.doesObjectExist(bucketName, imagePath) && !imagePath.equals("image/default.jpg")){
            cosclient.deleteObject(bucketName, imagePath);
            log.info("删除原有图片成功");
        }


        File localFile = null;
        try {
            localFile = File.createTempFile("temp", null);
            file.transferTo(localFile);
            // 指定要上传到 COS 上的路径
            String key = dir_name + "/"  + newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);

            String path = ImagePath + putObjectRequest.getKey();
            map.put("error_message", "success");
            map.put("photo", path);

            user.setPhoto(path);
            userDao.updateById(user);

        } catch (IOException e) {
            map.put("error_message", "fail");
            e.printStackTrace();
        } finally {
            // 关闭客户端(关闭后台线程)
            cosclient.shutdown();
        }

        return map;
    }
}
