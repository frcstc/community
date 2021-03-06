package com.hzgood.community.controller;

import com.hzgood.community.dto.AccessTokenDTO;
import com.hzgood.community.dto.GithubUser;
import com.hzgood.community.mapper.UserMapper;
import com.hzgood.community.model.User;
import com.hzgood.community.provider.GithubProvider;
import com.hzgood.community.provider.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JWTProvider jwtProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletRequest request, HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_url(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            User userRecord = userMapper.findByAccountId(githubUser.getId());
            if (userRecord == null) {
                // 登录成功 写cookie
                User user = new User();
                user.setName(githubUser.getLogin());
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setCreatedTime(System.currentTimeMillis());
                user.setUpdatedTime(System.currentTimeMillis());
                user.setAvatarUrl(githubUser.getAvatar_url());
                user.setBio(githubUser.getBio());
                userMapper.insert(user);
                userRecord = user;
            }
            String token = jwtProvider.getToken(userRecord);
            response.addCookie(new Cookie("jwtToken", token));
            request.getSession().setAttribute("user", userRecord);
            return "redirect:/"; // 重定向
        } else {
            return "redirect:/";
            // 登录失败，重新登录
        }
    }
}
