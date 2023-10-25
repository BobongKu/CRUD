package bobong.crud.domain.post.controller;

import bobong.crud.domain.post.cond.PostSearchCondition;
import bobong.crud.domain.post.dto.PostListDto;
import bobong.crud.domain.post.dto.PostSaveDto;
import bobong.crud.domain.post.dto.PostUpdateDto;
import bobong.crud.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;


    //FileUpload 때문에 json이 아닌 form 형태 구현
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void save(@Valid @ModelAttribute PostSaveDto postSaveDto) {
        postService.save(postSaveDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{postId}")
    public void update(@PathVariable Long postId,
                       @ModelAttribute PostUpdateDto postUpdateDto){
        postService.update(postId, postUpdateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }

    @GetMapping("/{postId}")
    public ResponseEntity getInfo(@PathVariable Long postId, String password){
        return ResponseEntity.ok(postService.getPostInfo(postId, password));
    }

    @GetMapping
    public ResponseEntity<PostListDto> getPostList(Pageable pageable, PostSearchCondition condition){
        return postService.getPostList(pageable, condition);
    }

}
