package com.hzgood.community.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String accountId;
    private String token;
    private String bio;
    private String avatarUrl;
    private Long createdTime;
    private Long updatedTime;
}
