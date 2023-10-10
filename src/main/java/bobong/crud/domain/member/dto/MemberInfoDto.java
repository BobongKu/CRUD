package bobong.crud.domain.member.dto;

import bobong.crud.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfoDto {

    private String nickName;
    private String username;
    private String role;

    @Builder
    public MemberInfoDto(Member member){
        this.nickName = member.getNickname();
        this.username = member.getUsername();
        this.role = member.getRole().toString();
    }
}
