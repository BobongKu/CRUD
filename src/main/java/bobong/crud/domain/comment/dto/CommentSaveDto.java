package bobong.crud.domain.comment.dto;

import bobong.crud.domain.comment.entity.Comment;

public record CommentSaveDto(String content) {

    public Comment toEntity() {
        return Comment.builder().content(content).build();
    }
}
