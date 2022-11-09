package com.hnieacm.media.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String name;
    private String password;
    private String mail;
    private Integer stars;
    //群组
    private String grp;

    //用户建立时间 存储格式为时间戳
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date date;
    //权限
    //0 admin
    //1 抽签管理员
    //2 普通用户
    private Integer admin;

    private boolean enable;

    private Long process;

    private String represent;

    private String photo;

    public User(Long id, String username, String name, String password, String mail, Integer stars, String grp, Date date, Integer admin, boolean enable, String photo) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.stars = stars;
        this.grp = grp;
        this.date = date;
        this.admin = admin;
        this.enable = enable;
        this.photo = photo;
    }
}
