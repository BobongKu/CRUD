package bobong.crud.domain.post.service;

import bobong.crud.domain.post.cond.PostSearchCondition;
import bobong.crud.domain.post.dto.PostInfoDto;
import bobong.crud.domain.post.dto.PostPagingDto;
import bobong.crud.domain.post.dto.PostSaveDto;
import bobong.crud.domain.post.dto.PostUpdateDto;
import bobong.crud.global.file.exception.FileException;

import java.awt.print.Pageable;

public interface PostService {

    void save(PostSaveDto postSaveDto);

    void update(Long id, PostUpdateDto postUpdateDto);

    void delete(Long id);

    PostInfoDto getPostInfo(Long id);

    PostPagingDto getPostList(Pageable pageable, PostSearchCondition postSearchCondition);
}
