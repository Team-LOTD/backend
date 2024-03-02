package LOTD.project.domain.member.repository;


import LOTD.project.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);
    Optional<Member> findByMemberId(String memberId);

    //boolean existsByLoginId(String loginId);

    boolean existsByNickName(String nickName);

    Optional<Member> findByNickName(String nickName);

}