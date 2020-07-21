package com.hzgood.community.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hzgood.community.annotations.JwtAuth;
import com.hzgood.community.annotations.PassToken;
import com.hzgood.community.model.User;
import com.hzgood.community.provider.UserContext;
import com.hzgood.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
public class JWTAuthInterceptor implements HandlerInterceptor {
    @Autowired
    protected UserService userService;

    @Value("${jwt.secret}")
    protected String jwtSecret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object object = request.getSession().getAttribute("user");
        if (object != null) {
            return true;
        }

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        if (method.isAnnotationPresent(JwtAuth.class)) {
            JwtAuth jwtAuth = method.getAnnotation(JwtAuth.class);
            if (jwtAuth.required()) {
                // 从cookie中获取token
                Cookie[] cookies = request.getCookies();
                String token = "";
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("jwtToken")) {
                        token = cookie.getValue();
                        break;
                    }
                }

                if (token == null) {
                    throw new RuntimeException("请登录");
                }

                // 验证token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException v) {
                    throw new RuntimeException("402");
                }

                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                User user = userService.findById(Long.valueOf(userId));
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                request.getSession().setAttribute("user", user);
//                // 加入线程threadLocal
//                UserContext.set(user);
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
