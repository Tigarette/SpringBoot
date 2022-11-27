package com.hnieacm.media.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnieacm.media.model.Media;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MediaDao extends BaseMapper<Media> {
}
