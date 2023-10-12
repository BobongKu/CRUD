package bobong.crud.domain.member.entity;

import bobong.crud.domain.BaseTimeEntity;
import bobong.crud.domain.comment.entity.Comment;
import bobong.crud.domain.member.Role;
import bobong.crud.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Table(name = "MEMBER")
@Getter
@Builder //생성시 가독성을 위해서 적용
@NoArgsConstructor(access = AccessLevel.PROTECTED) //다른 패키지 소속된 클래스는 접근 안됨
@AllArgsConstructor //빌더를 사용하려면 NoArgsConstructor 와 같이 사용해야된다.
@Entity
public class Member extends BaseTimeEntity {

    //컬럼//
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    //아이디
    @Column(nullable = false, length=30, unique = true)
    private String username;

    //비밀번호
    private String password;

    //닉네임
    @Column(nullable = false, length = 30)
    private String nickname;

    //권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    //회원가입시, USER권한 부여 메서드
    public void addUserAuthority(){
        this.role= Role.USER;
    }


    /* 회원탈퇴시 작성한 게시물, 댓글 모두 삭제 */
    @Builder.Default
    @OneToMany(mappedBy = "writer", cascade = ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "writer", cascade = ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();


    /*연관관계부분*/
    public void addPost (Post post) {
        postList.add(post);
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    /*정보 수정 부분*/

    //비밀번호 변경
    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }

    //닉네임 변경
    public void updateNickName(String nickName){
        this.nickname = nickName;
    }


    //Password Logic//

    //비밀번호 암호화로직
    public void encodedPassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    //정보변경을 위한 비밀번호 검증로직
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword,getPassword());
    }

}
