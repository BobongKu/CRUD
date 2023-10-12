package bobong.crud.domain.post.entity;

import bobong.crud.domain.BaseTimeEntity;
import bobong.crud.domain.comment.entity.Comment;
import bobong.crud.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    public void confirmWriter(Member member) {
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
}
