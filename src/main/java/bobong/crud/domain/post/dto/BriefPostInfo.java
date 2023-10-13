package bobong.crud.domain.post.dto;

import bobong.crud.domain.post.entity.Post;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Data
@NoArgsConstructor
public class BriefPostInfo {

    private Long postId;

    private String title;
    private String content;
    private String writerName;
    private String createdDate;

    public BriefPostInfo(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writerName = post.getWriter().getNickname();
        this.createdDate = post.getCreatedDate().toString();
    }

}
