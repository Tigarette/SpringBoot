package com.hnieacm.media.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Comments {

    @TableId(type = IdType.AUTO)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date time;
    private Long userid;
    private String name;
    private String message;
    private String photo;

    public Comments(Long id, Date time, Long userid, String name, String message, String photo) {
        this.id = id;
        this.time = time;
        this.userid = userid;
        this.name = name;
        this.message = message;
        this.photo = photo;
    }
}
