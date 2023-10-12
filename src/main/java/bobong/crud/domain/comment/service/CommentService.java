package bobong.crud.domain.comment.service;

import bobong.crud.domain.comment.dto.CommentSaveDto;
import bobong.crud.domain.comment.dto.CommentUpdateDto;
import bobong.crud.domain.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    void save(Long postId, CommentSaveDto commentSaveDto);
    void saveReComment(Long postId, Long parentId, CommentSaveDto commentSaveDto);

    void update(Long id, CommentUpdateDto commentUpdateDto);

    void remove(Long id);
}
