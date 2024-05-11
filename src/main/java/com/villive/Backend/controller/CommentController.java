package com.villive.Backend.controller;

import com.villive.Backend.domain.Posts;
import com.villive.Backend.dto.CommentRequestDto;
import com.villive.Backend.dto.CommentResponseDto;
import com.villive.Backend.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/posts/comment")
@RequiredArgsConstructor
@Tag(name = "게시판 댓글", description = "게시판 댓글 API")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/{postsId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postsId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return ResponseEntity.ok(commentService.createComment(commentRequestDto, postsId, request));
    }

    // 댓글 삭제
    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request) {
        commentService.deleteComment(postId, commentId, request);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "댓글이 삭제되었습니다.");
        responseBody.put("commentId", commentId);

        return ResponseEntity.ok().body(responseBody);
    }



}
