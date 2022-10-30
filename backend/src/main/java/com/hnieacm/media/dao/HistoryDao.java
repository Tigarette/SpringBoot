package com.hnieacm.media.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnieacm.media.model.History;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryDao extends BaseMapper<History> {
}
