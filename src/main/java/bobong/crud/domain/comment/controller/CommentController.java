package bobong.crud.domain.comment.controller;

import bobong.crud.domain.comment.dto.CommentSaveDto;
import bobong.crud.domain.comment.dto.CommentUpdateDto;
import bobong.crud.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void commentSave(@PathVariable("postId") Long postId, CommentSaveDto commentSaveDto) {
        commentService.save(postId, commentSaveDto);
    }

    @PostMapping("/comment/{postId}/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void reCommentSave(@PathVariable("postId") Long postId,
                              @PathVariable("commentId") Long commentId,
                              CommentSaveDto commentSaveDto){
        commentService.saveReComment(postId, commentId, commentSaveDto);
    }


    @PutMapping("/comment/{commentId}")
    public void update(@PathVariable("commentId") Long commentId,
                       CommentUpdateDto commentUpdateDto){
        commentService.update(commentId, commentUpdateDto);
    }


    @DeleteMapping("/comment/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId){
        commentService.remove(commentId);
    }
}
