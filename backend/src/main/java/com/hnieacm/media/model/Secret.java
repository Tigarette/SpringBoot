package com.hnieacm.media.model;

import com.hnieacm.media.Service.Impl.utils.ConnectTencent;
import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@AutoConfigureBefore(ConnectTencent.class)
@Configuration
@PropertySource("classpath:secret.properties")
@ConfigurationProperties(prefix = "storage")
@Data
public class Secret {
    private String secretId;
    private String secretKey;
    private String bucket;
}
