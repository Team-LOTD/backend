package LOTD.project.domain.Member.oauth2.service;

import LOTD.project.domain.Member.Member;
import LOTD.project.domain.Member.Role;
import LOTD.project.domain.Member.dto.response.LoginResponse;
import LOTD.project.domain.Member.oauth2.SocialType;
import LOTD.project.domain.Member.oauth2.dto.response.KakaoMember;
import LOTD.project.domain.Member.oauth2.dto.response.KakaoToken;
import LOTD.project.domain.Member.repository.MemberRepository;
import LOTD.project.global.jwt.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Transactional
@Service
public class KakaoService {

    @Value("${oauth.kakao.client_id}")
    String clientId;
    @Value("${oauth.kakao.redirect_uri}")
    String redirectUri;
    @Value("${oauth.kakao.client_secret}")
    String clientSecret;


    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    public LoginResponse kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {

        KakaoToken kakaoToken = getKakaoToken(code);

        String accessToken = kakaoToken.getAccessToken();

        Member member = saveMember(accessToken);



        LoginResponse loginResponse = jwtService.createJwtToken(member.getMemberId());

        jwtService.setHeaderAccessToken(response,loginResponse.getAccessToken());
        jwtService.setHeaderRefreshToken(response,loginResponse.getRefreshToken());

        return LoginResponse.builder()
                .id(member.getId())
                .grantType(loginResponse.getGrantType())
                .accessToken(loginResponse.getAccessToken())
                .accessTokenExpiresIn(loginResponse.getAccessTokenExpiresIn())
                .refreshToken(loginResponse.getRefreshToken())
                .refreshTokenExpiresIn(loginResponse.getRefreshTokenExpiresIn())
                .build();
    }

     private KakaoToken getKakaoToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("client_secret",clientSecret);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String tokenType = jsonNode.get("token_type").asText();
        String accessToken = jsonNode.get("access_token").asText();
        int expiresIn = jsonNode.get("expires_in").asInt();
        String refreshToken = jsonNode.get("refresh_token").asText();
        int refreshTokenExpiresIn = jsonNode.get("refresh_token_expires_in").asInt();
        String scope = jsonNode.get("scope").asText();

        KakaoToken kakaoToken = KakaoToken.builder()
                .tokenType(tokenType)
                .accessToken(accessToken)
                .expiresIn(expiresIn)
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshTokenExpiresIn)
                .scope(scope)
                .build();

        return kakaoToken;
    }


    /**
     * 카카오 회원가입 (첫 로그인 시만)
     * @param accessToken
     * @return
     * @throws JsonProcessingException
     */
    private Member saveMember(String accessToken) throws JsonProcessingException {
        JsonNode jsonNode = getKakaoInfo(accessToken);

        // DB 에 중복된 Kakao Id 가 있는지 확인
        String kakaoId = String.valueOf(jsonNode.get("id").asLong()) + "@kakao";
        Member kakaoMember = memberRepository.findByMemberId(kakaoId).orElse(null);


        if (kakaoMember == null) {
            String email = null;

            if (jsonNode.get("kakao_account").get("account_email") != null) {
                email = jsonNode.get("kakao_account").get("account_email").asText();

            }

            Member member = Member.builder()
                    .memberId(kakaoId)
                    .nickName("kakao_nickname")
                    .email(email)
                    .role(Role.MEMBER)
                    .socialType(SocialType.KAKAO)
                    .build();

            memberRepository.save(member);
            return member;
        }
        return kakaoMember;
    }





    /**
     * accessToken으로 카카오 사용자 정보 얻기
     */
    private JsonNode getKakaoInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(responseBody);

    }


}
