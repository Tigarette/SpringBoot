package com.hnieacm.media.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


@Data
public class History {
    @TableId(type = IdType.AUTO)
    private Long id;
    //单位秒
    private double process;
    private Long userid;
    private String video;
}
