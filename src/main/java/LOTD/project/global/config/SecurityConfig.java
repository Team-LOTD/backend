package LOTD.project.global.config;


import LOTD.project.global.exception.CustomAccessDeniedHandler;
import LOTD.project.global.exception.CustomAuthenticationEntryPoint;
import LOTD.project.global.jwt.JwtAuthenticationFilter;
import LOTD.project.global.jwt.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private static final String[] PERMIT_URL = {
            "/signUp", "/login", "/logout", "/memberId/check", "/nickname/check", "/oauth/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .formLogin().disable() // FormLogin 사용 X
                .httpBasic().disable() // httpBasic 사용 X

                .csrf().disable() // csrf 보안 사용 X
                .logout().disable()
                // 세션 사용하지 않으므로 STATELESS로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()

                //== URL별 권한 관리 옵션 ==//
                .authorizeRequests()

                .antMatchers("/members/**").hasRole("MEMBER")
                .antMatchers(PERMIT_URL).permitAll() // 회원가입, 로그인 폼 접근 가능
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtService),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("<http://localhost:3000>", "..."));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Authorization-refresh", "Cache-Control", "Content-Type"));

        /* 응답 헤더 설정 추가*/
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Authorization-refresh"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}