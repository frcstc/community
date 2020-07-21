package com.hzgood.community.model;

import lombok.Data;

@Data
public class Article {
    private Long id;
    private String title;
    private String content;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tags;
    private Long createdTime;
    private Long updatedTime;
}
