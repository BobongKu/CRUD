package bobong.crud.domain.member.repository;

import bobong.crud.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username); //이름으로 유저 맵핑

}
