package bobong.crud.domain.post.service;

import bobong.crud.domain.member.repository.MemberRepository;
import bobong.crud.domain.post.cond.PostSearchCondition;
import bobong.crud.domain.post.dto.PostInfoDto;
import bobong.crud.domain.post.dto.PostPagingDto;
import bobong.crud.domain.post.dto.PostSaveDto;
import bobong.crud.domain.post.dto.PostUpdateDto;
import bobong.crud.domain.post.entity.Post;
import bobong.crud.domain.post.exception.PostException;
import bobong.crud.domain.post.exception.PostExceptionType;
import bobong.crud.domain.post.repository.PostRepository;
import bobong.crud.global.file.exception.FileException;
import bobong.crud.global.file.exception.FileExceptionType;
import bobong.crud.global.file.service.FileService;
import bobong.crud.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Override
    public void save(PostSaveDto postSaveDto){
        Post post = postSaveDto.toEntity();

        //SecurityContextHolder에서 유저 정보 가져와 writer 이름 지정
        post.confirmWriter(memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UsernameNotFoundException("이잉")));

        postSaveDto.uploadFile().ifPresent(file -> {
            try {
                post.updateFilePath(fileService.save(file));
            } catch (Exception e) {
                throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);
            }
        });

        postRepository.save(post);
    }

    @Override
    public void update(Long id, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_POUND));

        checkAuthority(post, PostExceptionType.NOT_AUTHORITY_UPDATE_POST);

        //제목,내용 처리
        postUpdateDto.title().ifPresent(post::updateTitle);
        postUpdateDto.content().ifPresent(post::updateContent);

        //수정 후 기존 파일이 없어졌을 시 삭제
        if(post.getFilePath() != null) {
            fileService.delete(post.getFilePath());
        }

        //첨부파일 처리
        postUpdateDto.uploadFile().ifPresentOrElse(
                multipartFile -> {
                    try {
                        post.updateFilePath(fileService.save(multipartFile));
                    } catch (Exception e) {
                        throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);
                    }
                }
                , () -> post.updateFilePath(null));
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostException(PostExceptionType.POST_NOT_POUND));

        checkAuthority(post,PostExceptionType.NOT_AUTHORITY_DELETE_POST);

        //기존에 올린 파일 지우기
        if(post.getFilePath() !=null){
            fileService.delete(post.getFilePath());
        }

        postRepository.delete(post);
    }

    @Override
    public PostInfoDto getPostInfo(Long id) {
        return new PostInfoDto(postRepository.findById(id).orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_POUND)));
    }

    @Override
    public PostPagingDto getPostList(Pageable pageable, PostSearchCondition postSearchCondition) {

        return new PostPagingDto(postRepository.search(postSearchCondition, pageable));
    }

    private void checkAuthority(Post post, PostExceptionType postExceptionType) {
        if(!post.getWriter().getUsername().equals(SecurityUtil.getLoginUsername()))
            throw new PostException(postExceptionType);
    }
}
