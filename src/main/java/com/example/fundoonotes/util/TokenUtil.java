package com.example.fundoonotes.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import com.example.fundoonotes.exception.CustomException;
import com.example.fundoonotes.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class TokenUtil {

    private static final String TOKEN_SECRET = "Shalesh";
    private final RedisTemplate<String, Object> redisTemplate;
    private final String REDIS_KEY_PREFIX = "jwt:";

    public TokenUtil(RedisTemplate<String, Object> redisTemplate) {

        this.redisTemplate = redisTemplate;
    }

    public String createToken(int id) {
        try {
            // to set algorithm
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            String token = JWT.create().withClaim("user_id", id).sign(algorithm);
            // add token to Redis cache with 10 minutes expiration
            redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + token, "", Duration.ofMinutes(60));
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
            // log Token Signing Failed
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int verifyToken(String token) {
        int userId = -1;
        // for verification algorithm
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        JWTVerifier jwtverifier = verification.build();

        log.info(redisTemplate.keys(REDIS_KEY_PREFIX + token).toString());
        // to decode token
        DecodedJWT decodedjwt = jwtverifier.verify(token);

        Claim claim = decodedjwt.getClaim("user_id");
        userId = claim.asInt();

        // check if token exists in Redis cache
        if (redisTemplate.hasKey(REDIS_KEY_PREFIX + token)) {
            return userId;
        } else {
            return -1;
        }
    }
    public boolean isValid(String token){
        int userId = -1;
        // for verification algorithm
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        JWTVerifier jwtverifier = verification.build();

        log.info(redisTemplate.keys(REDIS_KEY_PREFIX + token).toString());
        // to decode token
        DecodedJWT decodedjwt = jwtverifier.verify(token);

        Claim claim = decodedjwt.getClaim("user_id");
        userId = claim.asInt();

        // check if token exists in Redis cache
        if (redisTemplate.hasKey(REDIS_KEY_PREFIX + token)) {
            return true;
        } else {
            return false;
        }
    }


}
