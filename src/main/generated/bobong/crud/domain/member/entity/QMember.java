package bobong.crud.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -201428730L;

    public static final QMember member = new QMember("member1");

    public final bobong.crud.domain.QBaseTimeEntity _super = new bobong.crud.domain.QBaseTimeEntity(this);

    public final ListPath<bobong.crud.domain.comment.entity.Comment, bobong.crud.domain.comment.entity.QComment> commentList = this.<bobong.crud.domain.comment.entity.Comment, bobong.crud.domain.comment.entity.QComment>createList("commentList", bobong.crud.domain.comment.entity.Comment.class, bobong.crud.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<bobong.crud.domain.post.entity.Post, bobong.crud.domain.post.entity.QPost> postList = this.<bobong.crud.domain.post.entity.Post, bobong.crud.domain.post.entity.QPost>createList("postList", bobong.crud.domain.post.entity.Post.class, bobong.crud.domain.post.entity.QPost.class, PathInits.DIRECT2);

    public final EnumPath<bobong.crud.domain.member.Role> role = createEnum("role", bobong.crud.domain.member.Role.class);

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

