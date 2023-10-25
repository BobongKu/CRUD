package bobong.crud.domain.post.service;

import bobong.crud.domain.member.repository.MemberRepository;
import bobong.crud.domain.post.PostType;
import bobong.crud.domain.post.cond.PostSearchCondition;
import bobong.crud.domain.post.dto.PostInfoDto;
import bobong.crud.domain.post.dto.PostListDto;
import bobong.crud.domain.post.dto.PostSaveDto;
import bobong.crud.domain.post.dto.PostUpdateDto;
import bobong.crud.domain.post.entity.Post;
import bobong.crud.domain.post.exception.PostErrorCode;
import bobong.crud.domain.post.exception.PostException;
import bobong.crud.domain.post.repository.PostQueryRepository;
import bobong.crud.domain.post.repository.PostRepository;
import bobong.crud.global.file.exception.FileErrorCode;
import bobong.crud.global.file.exception.FileException;
import bobong.crud.global.file.service.FileService;
import bobong.crud.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static bobong.crud.domain.post.PostType.HIDDEN;


@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final PostQueryRepository postQueryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(PostSaveDto postSaveDto){
        Post post = postSaveDto.toEntity();

        //SecurityContextHolder에서 유저 정보 가져와 writer 이름 지정
        post.confirmWriter(memberRepository.findByUsername(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UsernameNotFoundException("이잉")));


        if(postSaveDto.password().isEmpty()){
            //초기에 type Normal로 지정
            post.setPostTypeNORMAL();
        } else {
            //만약 비밀번호가 존재 할 시 비밀 글 처리
            postSaveDto.password().ifPresent(password -> {
                post.setPostTyeHIDDEN();
                post.updatePassword(password, passwordEncoder);
            });
        }

        //파일처리
        postSaveDto.uploadFile().ifPresent(file -> {
            try {
                post.updateFilePath(fileService.save(file));
            } catch (Exception e) {
                throw new FileException(FileErrorCode.FILE_CAN_NOT_SAVE);
            }
        });

        postRepository.save(post);
    }

    @Override
    public void update(Long id, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        checkAuthority(post, PostErrorCode.NOT_AUTHORITY_UPDATE_POST);

        //제목,내용 처리
        postUpdateDto.title().ifPresent(post::updateTitle);
        postUpdateDto.content().ifPresent(post::updateContent);

        //PostType 처리
        if(postUpdateDto.password().isEmpty()){
            post.setPostTypeNORMAL();
            post.removePassword();
        } else {
            post.setPostTyeHIDDEN();
            postUpdateDto.password().ifPresent(password -> {
                post.updatePassword(password, passwordEncoder);
            });
        }

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
                        throw new FileException(FileErrorCode.FILE_CAN_NOT_SAVE);
                    }
                }
                , () -> post.updateFilePath(null));
    }

    @Override
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostException(PostErrorCode.POST_NOT_FOUND));

        checkAuthority(post,PostErrorCode.NOT_AUTHORITY_DELETE_POST);

        //기존에 올린 파일 지우기
        if(post.getFilePath() !=null){
            fileService.delete(post.getFilePath());
        }

        postRepository.delete(post);
    }

    /**
     *
     * @param id
     * @param password
     * @return Post Information
     */
    @Override
    public PostInfoDto getPostInfo(Long id, String password) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        if(post.getPostType().equals(HIDDEN)){
            if(post.matchPassword(passwordEncoder, password)){
                return new PostInfoDto(post);
            } else {
                throw new PostException(PostErrorCode.NOT_AUTHORITY_POST);
            }
        }

        return new PostInfoDto(post);
    }

    @Override
    public ResponseEntity<PostListDto> getPostList(Pageable pageable, PostSearchCondition condition) {

        Page<Post> results = postQueryRepository.getPostList(pageable, condition);

        return new ResponseEntity<>(PostListDto.builder()
                .postList(results.getContent())
                .totalCount(results.getTotalElements())
                .totalPages((long)results.getTotalPages())
                .build(), HttpStatus.OK);
    }


    //사용자 검증
    private void checkAuthority(Post post, PostErrorCode postErrorCode) {
        if(!post.getWriter().getUsername().equals(SecurityUtil.getLoginUsername()))
            throw new PostException(postErrorCode);
    }
}
