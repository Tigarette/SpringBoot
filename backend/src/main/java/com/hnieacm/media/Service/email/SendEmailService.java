package com.hnieacm.media.Service.email;

import java.util.Map;

public interface SendEmailService {
    Map<String, String> sendSimpleMail(String email);
}
