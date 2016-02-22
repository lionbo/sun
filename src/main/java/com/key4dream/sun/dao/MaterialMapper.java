package com.key4dream.sun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.key4dream.sun.entity.Material;

@MyBatisRepository
public interface MaterialMapper {

    @Select("SELECT * FROM material")
    @Results(value = {
            @Result(id = true, property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT) })
    List<Material> findAll();

    @Insert("insert into material(mid,content) values(#{mid},#{content})")
    Boolean add(Material material);

    @Select("select * from material where id<#{currentId} order by id desc limit #{limitNum}")
    List<Material> find(@Param("currentId") Long currentId, @Param("limitNum") Integer limitNum);

    @Select("select max(id) from material")
    Long findMaxId();
}
