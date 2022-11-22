package com.hnieacm.media;

import com.hnieacm.media.model.Secret;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class MediaApplicationTests {
    @Autowired
    private Secret secret;
    @Test
    void contextLoads() {
        log.info(secret.getSecretId());
    }

}
