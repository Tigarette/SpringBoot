package com.hnieacm.media.model;

import com.hnieacm.media.Service.Impl.utils.ConnectTencent;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@AutoConfigureBefore(ConnectTencent.class)
@Configuration
@PropertySource("classpath:secret.properties")
@Data
public class Secret {
    @Value("${storage.secretId}")
    private String secretId;
    @Value("${storage.secretKey}")
    private String secretKey;
    @Value("${storage.bucket}")
    private String bucket;
}
