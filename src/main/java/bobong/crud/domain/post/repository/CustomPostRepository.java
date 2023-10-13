package bobong.crud.domain.post.repository;

import bobong.crud.domain.post.cond.PostSearchCondition;
import bobong.crud.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPostRepository {

    Page<Post> search(PostSearchCondition postSearchCondition, Pageable pageable);
}
