package com.hzgood.community.controller;

import com.hzgood.community.annotations.JwtAuth;
import com.hzgood.community.model.Article;
import com.hzgood.community.model.User;
import com.hzgood.community.provider.SessionProvider;
import com.hzgood.community.provider.UserContext;
import com.hzgood.community.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

@Controller
public class PublishController {
    @Resource
    protected ArticleService articleService;

    @JwtAuth
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @JwtAuth
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("tags") String tags,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("tags", tags);
        if (StringUtils.isEmpty(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isEmpty(content)) {
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        if (StringUtils.isEmpty(tags)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        Article article = new Article();
        article.setCreatedTime(System.currentTimeMillis());
        article.setUpdatedTime(System.currentTimeMillis());
        article.setTags(tags);
        article.setTitle(title);
        article.setContent(content);
        article.setCreator(SessionProvider.getSessionUser().getId());
        articleService.publish(article);
        return "redirect:/";
    }
}
