package bobong.crud.domain.post.dto;

import bobong.crud.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostListDto {

    private List<PostDto> postInfoDtoList = new ArrayList<>();

    private Long totalPages;

    private Long totalCount;

    @Builder
    public PostListDto(List<Post> postList, Long totalPages, Long totalCount) {
        this.postInfoDtoList = postList.stream().map(PostDto::new).collect(Collectors.toList());
        this.totalPages = totalPages;
        this.totalCount = totalCount;
    }
}
