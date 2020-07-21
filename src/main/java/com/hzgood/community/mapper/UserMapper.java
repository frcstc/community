package com.hzgood.community.mapper;

import com.hzgood.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name, account_id, avatar_url, bio, created_time, updated_time) values(#{name}, #{accountId},  #{avatarUrl}, #{bio}, #{createdTime}, #{updatedTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void insert(User user);

    @Select("select * from user where id = #{id}" )
    public User findById(Long id);

    @Select("select * from user where account_id = #{accountId}")
    public User findByAccountId(Long accountId);
}
