package com.hnieacm.media.Service.user;

import java.util.Map;

public interface ChangePasswordService {
    Map<String, String> changePassword(String oldPassword, String password, String conformPassword);
}
