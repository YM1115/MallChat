package com.abin.mallchat.common.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * jwt的token生成与解析
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">abin</a>
 * @since 2023-04-03
 */
@Slf4j
@Component
public class JwtUtils {

    /**
     * token秘钥，请勿泄露，请勿随便修改
     */
    @Value("jwt.secret")
    private String secret;

    private static final String UID_CLAIM = "uid";
    private static final String CREATE_TIME = "createTime";

    /**
     * JWT生成Token.<br/>
     * <p>
     * JWT构成: header, payload, signature
     */
    public String createToken(Long uid) {
        // build token
        return JWT.create()
                // 只存一个uid信息，其他的自己去redis查
                .withClaim(UID_CLAIM, uid)
                .withClaim(CREATE_TIME, new Date())
                // signature
                .sign(Algorithm.HMAC256(secret));
    }

    public static void main(String[] args) {
        JwtUtils jwtUtils = new JwtUtils();
        String token = jwtUtils.createToken(123L);
        System.out.println(token);
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     */
    public Map<String, Claim> verifyToken(String token) {
        if (Objects.isNull(token)) {
            return null;
        }
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception e) {
            log.info("decode error,token:{}", token, e);
        }
        return null;
    }


    /**
     * 根据Token获取uid
     *
     * @param token
     * @return uid
     */
    public Long getUidOrNull(String token) {
        return Optional.ofNullable(verifyToken(token))
                .map(map -> map.get(UID_CLAIM))
                .map(Claim::asLong)
                .orElse(null);
    }

}
