package com.hnieacm.media.Service.Impl.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectTencent {
    @Bean
    public COSClient COSClient(){

        final String secretId="***";     				//腾讯云对象存储的secretId(需替换)
        final String secretKey="****";          			//腾讯云对象存储的secretKey(需替换)
        final String bucket="ap-guangzhou";                             //所属地域

        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));

        // 3 生成cos客户端
        return new COSClient(cred, clientConfig);
    }
}
