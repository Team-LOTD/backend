package LOTD.project.global.login.service;

import LOTD.project.domain.Member.Member;
import LOTD.project.domain.Member.Role;
import LOTD.project.domain.Member.repository.MemberRepository;
import LOTD.project.global.exception.BaseException;
import LOTD.project.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        Member member = memberRepository.findByMemberId(memberId)
                .orElse(null);

        if (member == null) {
            request.setAttribute("exception",new BaseException(ExceptionCode.NOT_EXIST_MEMBER));
            throw new BaseException(ExceptionCode.NOT_EXIST_MEMBER);
        }

        List<GrantedAuthority> authority = List.of(new SimpleGrantedAuthority(member.getRole().getKey())); // 권한 넣어주기


        return org.springframework.security.core.userdetails.User.builder()
                    .username(member.getMemberId())
                    .password(member.getPassword())
                    .authorities(authority).build();


    }

}
