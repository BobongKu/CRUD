package bobong.crud.domain.post.dto;

import bobong.crud.domain.comment.dto.CommentInfoDto;
import bobong.crud.domain.member.dto.MemberInfoDto;
import bobong.crud.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostDto {

    private Long postId; //POST의 ID
    private String title;//제목
    private String content;//내용
    private String filePath;//업로드 파일 경로
    private MemberInfoDto writerDto;//작성자에 대한 정보

    public PostDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.filePath = post.getFilePath();
        this.writerDto = new MemberInfoDto(post.getWriter());
    }
}