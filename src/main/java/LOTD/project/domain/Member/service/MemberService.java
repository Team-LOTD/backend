package LOTD.project.domain.Member.service;


import LOTD.project.domain.Member.Member;
import LOTD.project.domain.Member.Role;
import LOTD.project.domain.Member.dto.request.MemberLoginRequest;
import LOTD.project.domain.Member.dto.request.MemberSignUpRequest;
import LOTD.project.domain.Member.dto.response.LoginResponse;
import LOTD.project.domain.Member.repository.MemberRepository;
import LOTD.project.global.exception.BaseException;
import LOTD.project.global.exception.ExceptionCode;
import LOTD.project.global.jwt.JwtService;
import LOTD.project.global.login.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RedisService redisService;

    public boolean checkMemberId(String memberId) {
        if (memberRepository.findByMemberId(memberId).isPresent()){
            return false;
        }
        else{
            return true;
        }
    }


    public boolean checkNickname(String nickname) {
        if (memberRepository.findByNickName(nickname).isPresent()){
            return false;
        }
        else{
            return true;
        }
    }



    /**
     * 회원가입
     */

    @Transactional
    public Member signUp(MemberSignUpRequest memberSignUpRequest) throws Exception {

        if (memberRepository.findByMemberId(memberSignUpRequest.getMemberId()).isPresent()){
            throw new BaseException(ExceptionCode.EXIST_MEMBER_ID);
        }

        Member member = Member.builder()
                .memberId(memberSignUpRequest.getMemberId())
                .password(memberSignUpRequest.getPassword())
                .nickName(memberSignUpRequest.getNickName())
                .email(memberSignUpRequest.getEmail())
                .role(Role.MEMBER)
                .socialType(null)
                .build();

        // 패스워드 암호화
        member.passwordEncode(passwordEncoder);

        // 회원 저장 (회원가입)
        memberRepository.save(member);
        return member;
    }

    public Member findMember(String memberId){
        return memberRepository.findByMemberId(memberId).orElse(null);
    }

    public LoginResponse login(Member member, MemberLoginRequest memberLoginRequest){
        if (!member.matchPassword(passwordEncoder,memberLoginRequest.getPassword())) {
            return null;
        }
        else{
            LoginResponse loginResponse = jwtService.createJwtToken(member.getMemberId());

            redisService.setRefreshToken(member.getMemberId(), loginResponse.getRefreshToken()); // refreshToken redis에 저장
               return LoginResponse.builder()
                       .id(member.getId())
                       .grantType(loginResponse.getGrantType())
                       .accessToken(loginResponse.getAccessToken())
                       .accessTokenExpiresIn(loginResponse.getAccessTokenExpiresIn())
                       .refreshToken(loginResponse.getRefreshToken())
                       .refreshTokenExpiresIn(loginResponse.getRefreshTokenExpiresIn())
                       .socialType(null) // 자체 로그인이므로 소셜 타입은 null
                       .build();
            }
    }

    public void logout(String accessToken) throws Exception {
        // AccessToken을 블랙리스트에 추가
        redisService.addToBlacklist(accessToken);

        // RefreshToken을 Redis에서 삭제
        redisService.delRefreshToken(jwtService.getMemberId(accessToken));
        System.out.println("d" + redisService.getRefreshToken(jwtService.getMemberId(accessToken)));
        // SecurityContextHolder에 있는 Authentication 정보도 삭제해준다.
        SecurityContextHolder.clearContext();
    }
}
