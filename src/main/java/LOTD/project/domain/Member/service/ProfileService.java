package LOTD.project.domain.Member.service;

import LOTD.project.domain.Member.Member;
import LOTD.project.domain.Member.dto.request.MemberUpdateEmailRequest;
import LOTD.project.domain.Member.dto.request.MemberUpdateNickNameRequest;
import LOTD.project.domain.Member.dto.response.MyPageResponse;
import LOTD.project.domain.Member.repository.MemberRepository;
import LOTD.project.global.exception.BaseException;
import LOTD.project.global.exception.ExceptionCode;
import LOTD.project.global.login.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    /**
     * 회원정보 수정 ( 닉네임 ,나이 등 )
     * @param memberUpdateNickNameRequest
     */
    public void updateMemberNickName(MemberUpdateNickNameRequest memberUpdateNickNameRequest, Long id){
        Member member = memberRepository.findById(id).orElse(null);

        if (memberUpdateNickNameRequest.getNickName() != null){
            member.updateNickname(memberUpdateNickNameRequest.getNickName());
        }

    }

    public void updateMemberEmail(MemberUpdateEmailRequest memberUpdateEmailRequest, Long id){
        Member member = memberRepository.findById(id).orElse(null);

        if (memberUpdateEmailRequest.getEmail() != null){
            member.updateEmail(memberUpdateEmailRequest.getEmail());
        }

    }

    /**
     * 비밀번호 변경
     * @param asIsPassword
     * @param toBePassword
     */
    public void changePassword(String asIsPassword, String toBePassword, Long id) {
        Member member = memberRepository.findById(id).orElse(null);

        if (member != null) {
            if (!member.matchPassword(passwordEncoder,asIsPassword)){
                throw new BaseException(ExceptionCode.WRONG_PASSWORD);
            }
            member.changePassword(passwordEncoder,toBePassword);
        }
    }

    /**
     * 회원 탈퇴
     */

    public void delMember(String checkPassword, Long id){
        Member member = memberRepository.findById(id).orElse(null);

        if (member != null) {
            if (!member.matchPassword(passwordEncoder, checkPassword)) {
                throw new BaseException(ExceptionCode.WRONG_PASSWORD);
            }

            // RefreshToken을 Redis에서 삭제
            redisService.delRefreshToken(member.getMemberId());
            
            // 회원 DB에서 삭제
            memberRepository.delete(member);


        }

    }

    public MyPageResponse myPage(Long id){
        Member member = memberRepository.findById(id).orElse(null);

        if (member == null) {
            throw new BaseException(ExceptionCode.NOT_EXIST_MEMBER);
        }
        else {
            return MyPageResponse.builder()
                    .memberId(member.getMemberId())
                    .nickName(member.getNickName())
                    .email(member.getEmail())
                    .build();
        }
    }

}
