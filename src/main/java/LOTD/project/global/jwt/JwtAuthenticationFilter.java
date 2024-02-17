package LOTD.project.global.jwt;

import LOTD.project.domain.Member.dto.response.LoginResponse;
import LOTD.project.global.exception.BaseException;
import LOTD.project.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    /**
     * 필터 거치지 않는 URL 설정 (로그인이 필요 없는 URL)
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/signup", "/login", "/logout", "/memberId/check", "/nickname/check", "/oauth/**"};
        // 제외할 url 설정
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String accessToken = jwtService.getAccessToken(request);

        /** AT가 null 이 아닌 경우 */
        if(accessToken != null){
            if (jwtService.validateToken(accessToken,request)) {
                // accessToken이 유효하면 Context에 Authentication 저장
                SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(accessToken));
            }
        }
        else{

            /** AT가 만료되었거나, 유효하지 않은 경우
             * RT로 AT 재발급 및 API 요청 처리
             */
            String refreshToken = jwtService.getRefreshToken(accessToken);
            if (refreshToken != null){
                if (jwtService.validateToken(refreshToken,request)) {
                    String memberId = jwtService.getMemberId(refreshToken);
                    LoginResponse loginresponse = jwtService.createJwtToken(memberId);

                    jwtService.setHeaderAccessToken(response, loginresponse.getAccessToken());
                    jwtService.setHeaderRefreshToken(response, loginresponse.getRefreshToken());
                    this.setAuthentication(loginresponse.getAccessToken());
                }
            }

            else{
                request.setAttribute("exception",new BaseException(ExceptionCode.NOT_EXISTS_TOKEN));// 인증 토큰이 없음
            }
            
        }

        filterChain.doFilter(request, response);
    }
    public void setAuthentication(String accessToken) {
        Authentication authentication = jwtService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
