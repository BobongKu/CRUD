package bobong.crud.domain.comment.dto;

import java.util.Optional;

public record CommentUpdateDto(Optional<String> content) {
}
