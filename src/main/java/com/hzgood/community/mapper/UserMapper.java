package com.hzgood.community.mapper;

import com.hzgood.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name, account_id, token, avatar_url, bio, created_time, updated_time) values(#{name}, #{accountId}, #{token}, #{avatarUrl}, #{bio}, #{createdTime}, #{updatedTime})")
    public void insert(User user);

    @Select("select * from user where token = #{token}" )
    public User findByToken(String token);
}
