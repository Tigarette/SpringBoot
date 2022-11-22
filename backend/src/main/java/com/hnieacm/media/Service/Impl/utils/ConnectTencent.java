package com.hnieacm.media.Service.Impl.utils;

import com.hnieacm.media.model.Secret;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectTencent {
    @Autowired
    private Secret secret;
    @Bean
    public COSClient COSClient(){
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secret.getSecretId(), secret.getSecretKey());

        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(secret.getBucket()));

        // 3 生成cos客户端
        return new COSClient(cred, clientConfig);
    }
}
