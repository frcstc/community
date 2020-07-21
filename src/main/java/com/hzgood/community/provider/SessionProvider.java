package com.hzgood.community.provider;

import com.hzgood.community.model.User;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class SessionProvider {
    public static User getSessionUser() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return (User) requestAttributes.getRequest().getSession().getAttribute("user");
    }
}
