package bobong.crud.domain.comment.service;

import bobong.crud.domain.comment.dto.CommentSaveDto;
import bobong.crud.domain.comment.dto.CommentUpdateDto;
import bobong.crud.domain.comment.entity.Comment;
import bobong.crud.domain.comment.exception.CommentErrorCode;
import bobong.crud.domain.comment.exception.CommentException;
import bobong.crud.domain.comment.repository.CommentRepository;
import bobong.crud.domain.member.repository.MemberRepository;
import bobong.crud.domain.post.exception.PostErrorCode;
import bobong.crud.domain.post.exception.PostException;
import bobong.crud.domain.post.repository.PostRepository;
import bobong.crud.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public void save(Long postId, CommentSaveDto commentSaveDto) {
        Comment comment = commentSaveDto.toEntity();

        comment.confirmWriter(memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UsernameNotFoundException("이잉")));

        comment.confirmPost(postRepository.findById(postId).orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND)));

        commentRepository.save(comment);
    }

    @Override
    public void saveReComment(Long postId, Long parentId, CommentSaveDto commentSaveDto) {
        Comment comment = commentSaveDto.toEntity();

        comment.confirmWriter(memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UsernameNotFoundException("이잉")));

        comment.confirmPost(postRepository.findById(postId).orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND)));

        comment.confirmParent(commentRepository.findById(parentId).orElseThrow(() -> new CommentException(CommentErrorCode.NOT_POUND_COMMENT)));

        commentRepository.save(comment);
    }

    @Override
    public void update(Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentException(CommentErrorCode.NOT_POUND_COMMENT));

        if(!comment.getWriter().getUsername().equals(SecurityUtil.getLoginUsername())) {
            throw new CommentException(CommentErrorCode.NOT_AUTHORITY_UPDATE_COMMENT);
        }

        commentUpdateDto.content().ifPresent(comment::updateContent);
    }

    @Override
    public void remove(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentException(CommentErrorCode.NOT_POUND_COMMENT));
        comment.remove(); //쿼리 수정
        List<Comment> removableCommentList = comment.findRemovableList();
        removableCommentList.forEach(commentRepository::delete); //실제 삭제
    }
}
