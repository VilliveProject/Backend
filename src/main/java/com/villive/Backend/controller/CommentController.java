package com.villive.Backend.controller;

import com.villive.Backend.domain.Posts;
import com.villive.Backend.dto.CommentRequestDto;
import com.villive.Backend.dto.CommentResponseDto;
import com.villive.Backend.dto.MsgResponseDto;
import com.villive.Backend.jwt.CustomUserDetails;
import com.villive.Backend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Operation(summary = "댓글 등록")
    @PostMapping("/{postsId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postsId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok(commentService.createComment(commentRequestDto, postsId, customUserDetails.getMember()));
    }

    // 댓글 삭제
    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<MsgResponseDto> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        commentService.deleteComment(postId, commentId, customUserDetails.getMember());
        return ResponseEntity.ok(commentService.deleteComment(postId, commentId, customUserDetails.getMember()));
    }



}
