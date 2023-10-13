package bobong.crud.domain.post.repository;

import bobong.crud.domain.post.entity.Post;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {

    @EntityGraph(attributePaths = {"writer"})
    Optional<Post> findWithWriterById(Long id);

}
