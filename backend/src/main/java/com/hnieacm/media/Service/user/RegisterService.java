package com.hnieacm.media.Service.user;

import java.util.Map;

public interface RegisterService {
    Map<String, String> register(String username, String name, String password, String conPassword,
                                 String group, String email, String code);
}
