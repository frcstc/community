package com.hzgood.community.mapper;

import com.hzgood.community.model.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article (title, content, creator, comment_count, view_count, like_count, tags, created_time, updated_time) values(#{title}, #{content}, #{creator}, #{commentCount}, #{viewCount}, #{likeCount}, #{tags}, #{createdTime}, #{updatedTime})")
    @Options(useGeneratedKeys = true,keyColumn = "id", keyProperty = "id")
    void insert(Article article);
}
