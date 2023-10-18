package bobong.crud.domain.post.service;

import bobong.crud.domain.post.dto.PostInfoDto;
import bobong.crud.domain.post.dto.PostPagingDto;
import bobong.crud.domain.post.dto.PostSaveDto;
import bobong.crud.domain.post.dto.PostUpdateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface PostService {

    void save(PostSaveDto postSaveDto);

    void update(Long id, PostUpdateDto postUpdateDto);

    void delete(Long id);

    PostInfoDto getPostInfo(Long id);

    ResponseEntity<PostPagingDto> getPostList(Pageable pageable);
}
