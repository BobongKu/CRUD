package bobong.crud.domain.post.dto;

import bobong.crud.domain.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public record PostSaveDto(@NotBlank(message = "제목을 입력해주세요") String title,
                          @NotBlank(message = "내용을 입력해주세요") String content,
                          Optional<MultipartFile> uploadFile,
                          Optional<String> password) {

    public Post toEntity() {
        return Post.builder().title(title).content(content).build();
    }
}
