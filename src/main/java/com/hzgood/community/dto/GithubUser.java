package com.hzgood.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private Long id;
    private String name;
    private String avatar_url;
    private String bio;
    private String login;
}
