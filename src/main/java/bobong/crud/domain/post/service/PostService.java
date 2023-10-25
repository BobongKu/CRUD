package bobong.crud.domain.post.service;

import bobong.crud.domain.post.cond.PostSearchCondition;
import bobong.crud.domain.post.dto.PostInfoDto;
import bobong.crud.domain.post.dto.PostListDto;
import bobong.crud.domain.post.dto.PostSaveDto;
import bobong.crud.domain.post.dto.PostUpdateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface PostService {

    void save(PostSaveDto postSaveDto);

    void update(Long id, PostUpdateDto postUpdateDto);

    void delete(Long id);

    PostInfoDto getPostInfo(Long id, String password);

    ResponseEntity<PostListDto> getPostList(Pageable pageable, PostSearchCondition condition);
}
