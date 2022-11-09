package com.hnieacm.media.Service.user;

import java.util.Map;

public interface ForgetPasswordService {
    Map<String, String> forgetPassword(String username, String password, String confirm, String email, String code);
}
