package com.hzgood.community.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hzgood.community.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTProvider {
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 生成token
     * @param user
     * @return
     */
    public String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(String.valueOf(user.getId())).sign(Algorithm.HMAC256(secret));
        return token;
    }
}
