package bobong.crud.domain.post.entity;

import bobong.crud.domain.BaseTimeEntity;
import bobong.crud.domain.comment.entity.Comment;
import bobong.crud.domain.member.entity.Member;
import bobong.crud.domain.post.PostType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Table(name ="POST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @Column(length = 40, nullable = false)
    private String title;

    //컨텐츠 특성상 255자 이상 저장되기 때문에 @Lob 어노테이션 사용
    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String filePath;

    //eunm상수 string으로 저장
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType postType;

    @Column(nullable = true)
    private String password;

    //Normal 타입으로 설정
    public void setPostTypeNORMAL() {
        this.postType = PostType.NORMAL;
    }

    //Hidden 타입으로 설정
    public void setPostTyeHIDDEN() {
        this.postType = PostType.HIDDEN;
    }

    //비밀번호 검증
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword,getPassword());
    }

    @Builder
    public Post(String title, String content, String filePath){
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }

    //게시글 삭제시 댓글 모두 삭제
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    /*연관관계 설정*/
    public void confirmWriter(Member writer) {
        this.writer = writer;
        writer.addPost(this);
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    /*내용 수정*/
    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void updatePassword(String password, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void removePassword() {
        this.password = null;
    }
}
