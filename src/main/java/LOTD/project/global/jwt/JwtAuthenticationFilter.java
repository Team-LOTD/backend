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

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private static final String LOGIN_URL = "/login"; // "/login"으로 들어오는 요청은 Filter 작동 X
    private static final String SIGNUP_URL = "/signUp"; // "/login"으로 들어오는 요청은 Filter 작동 X


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if (request.getRequestURI().equals(LOGIN_URL) || request.getRequestURI().equals(SIGNUP_URL) ) {
            filterChain.doFilter(request, response); // "/login, signUp" 요청이 들어오면, 다음 필터 호출
            return; // return으로 이후 현재 필터 진행 막아줌
        }

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
            /**
             * 로그인이 필요하지 않은 요청들 . ex) 닉네임, 아이디 중복 체크 여부 등
             */
            else{

                //request.setAttribute("exception",new BaseException(ExceptionCode.REFRESH_TOKEN_EXPIRED)); // REFRESH TOKEN 만료
            }
            
        }

        filterChain.doFilter(request, response);
    }
    public void setAuthentication(String accessToken) {
        Authentication authentication = jwtService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
