package bobong.crud.domain.post.controller;

import bobong.crud.domain.post.cond.PostSearchCondition;
import bobong.crud.domain.post.dto.PostSaveDto;
import bobong.crud.domain.post.dto.PostUpdateDto;
import bobong.crud.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post")
    public void save(@Valid @ModelAttribute PostSaveDto postSaveDto) {
        postService.save(postSaveDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/post/{postId}")
    public void update(@PathVariable("postId") Long postId,
                       @ModelAttribute PostUpdateDto postUpdateDto){

        postService.update(postId, postUpdateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/post/{postId}")
    public void delete(@PathVariable("postId") Long postId){
        postService.delete(postId);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity getInfo(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(postService.getPostInfo(postId));
    }
}