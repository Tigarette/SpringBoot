package com.hnieacm.media.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Media {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String path;
}
