package com.hnieacm.media.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnieacm.media.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("SELECT id, grp, name, SumProcess, mail FROM user as a, (SELECT Sum(process) as SumProcess, userid FROM history GROUP BY userid) as b WHERE a.id=b.userid ORDER BY SumProcess DESC LIMIT ${start},${end}")
    List<Map<String, Object>> ranklist(@Param("start") Integer start, @Param("end") Integer end);
}
