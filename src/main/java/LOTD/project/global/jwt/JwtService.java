package LOTD.project.global.jwt;


import LOTD.project.domain.Member.dto.response.LoginResponse;

import LOTD.project.global.exception.BaseException;
import LOTD.project.global.exception.ExceptionCode;
import LOTD.project.global.login.service.CustomUserDetailsService;
import LOTD.project.global.login.service.redis.RedisService;

import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private Key key;
    private final RedisService redisService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Value("${jwt.access.expiration}")
    private Long accessExpirePeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshExpirePeriod;

    public LoginResponse createJwtToken(String memberId){

        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now+ accessExpirePeriod);
        Date refreshTokenExpiresIn = new Date(now + refreshExpirePeriod);

        // Access Token 생성
        String accessToken = createAccessToken(memberId,accessTokenExpiresIn);
        // Refresh Token 생성
        String refreshToken = createRefreshToken(memberId,refreshTokenExpiresIn);

        return LoginResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshTokenExpiresIn(refreshTokenExpiresIn.getTime())
                .build();
    }

    public String createAccessToken(String memberId, Date expirationTime){
        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name())    // 토큰 제목
                .setIssuedAt(new Date())                // 토큰 발급 시간
                .setExpiration(expirationTime)          // 토큰 만료 시간
                .claim("memberId", memberId)                  // 회원 아이디
                .signWith(key)
                .compact();

    }

    public String createRefreshToken(String memberId, Date expirationTime) {
        return Jwts.builder()
                .setSubject(TokenType.REFRESH.name())   // 토큰 제목
                .setIssuedAt(new Date())                // 토큰 발급 시간
                .setExpiration(expirationTime)          // 토큰 만료 시간
                .claim("memberId", memberId)      // 회원 아이디
                .signWith(key)
                .setHeaderParam("typ", "JWT")
                .compact();
    }



    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드


    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token,HttpServletRequest request) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            request.setAttribute("exception",new BaseException(ExceptionCode.NOT_VALID_TOKEN));
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            request.setAttribute("exception", new BaseException(ExceptionCode.ACCESS_TOKEN_EXPIRED));
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            request.setAttribute("exception",new BaseException(ExceptionCode.UNSUPPORTED_TOKEN));
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            request.setAttribute("exception",new BaseException(ExceptionCode.NOT_ACCESS_TOKEN_TYPE));
        }
        return false;
    }


    // accessToken
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null){
            String accessToken = authorizationHeader.split(" ")[1]; // accesstoken 추출

            if (redisService.isTokenInBlacklist(accessToken)) {
                request.setAttribute("exception", new BaseException(ExceptionCode.BLACKLISTED_ACCESS_TOKEN));
                throw new BaseException(ExceptionCode.BLACKLISTED_ACCESS_TOKEN);
            }

            return accessToken;
        }
        return null;
    }

    public String getRefreshToken(String accessToken) {
        if (accessToken != null) {
            Claims claims = parseClaims(accessToken);
            String memberId = claims.get("memberId", String.class);
            String refreshToken = redisService.getRefreshToken(memberId);
            return refreshToken;
        }
        return null;
    }

    public Authentication getAuthentication(String accessToken) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getMemberId(accessToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    public String getMemberId(String refreshToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
        return claims.get("memberId", String.class);
    }


    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "Bearer " + accessToken);
    }
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("Authorization-refresh", "Bearer " + refreshToken);
    }
}


