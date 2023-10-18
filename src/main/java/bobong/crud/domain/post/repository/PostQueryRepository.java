package bobong.crud.domain.post.repository;

import bobong.crud.domain.member.entity.QMember;
import bobong.crud.domain.post.entity.Post;
import bobong.crud.domain.post.entity.QPost;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static bobong.crud.domain.member.entity.QMember.member;
import static bobong.crud.domain.post.entity.QPost.post;

@Repository
public class PostQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public PostQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<Post> getPostList(Pageable pageable) {
        QueryResults<Post> results = query
                .selectFrom(post)
                .leftJoin(post.writer, member).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.id.desc())
                .fetchResults();

        List<Post> content = results.getResults();
        Long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}