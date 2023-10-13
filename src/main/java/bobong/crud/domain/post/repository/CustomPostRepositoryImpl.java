package bobong.crud.domain.post.repository;

import bobong.crud.domain.member.entity.QMember;
import bobong.crud.domain.post.cond.PostSearchCondition;
import bobong.crud.domain.post.entity.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static bobong.crud.domain.post.entity.QPost.post;

@Repository
public class CustomPostRepositoryImpl implements CustomPostRepository {

    private final JPAQueryFactory query;
    public CustomPostRepositoryImpl (EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> search(PostSearchCondition postSearchCondition, Pageable pageable) {
        List<Post> content = query.selectFrom(post)
                .where(contentHasStr(postSearchCondition.getContent()),
                        titleHasStr(postSearchCondition.getTitle()))
                .leftJoin(post.writer, QMember.member)
                .fetchJoin()
                .orderBy(post.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = query.selectFrom(post)
                .where(contentHasStr(postSearchCondition.getContent()),
                        titleHasStr(postSearchCondition.getTitle()));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression titleHasStr(String title) {
        return StringUtils.hasLength(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression contentHasStr(String content) {
        return StringUtils.hasLength(content) ? post.content.contains(content) : null;
    }
}
