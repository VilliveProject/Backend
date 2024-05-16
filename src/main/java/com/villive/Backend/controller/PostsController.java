package com.villive.Backend.controller;

import com.villive.Backend.domain.PostsLike;
import com.villive.Backend.dto.MsgResponseDto;
import com.villive.Backend.dto.PostsRequestDto;
import com.villive.Backend.dto.PostsResponseDto;
import com.villive.Backend.jwt.CustomUserDetails;
import com.villive.Backend.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Tag(name = "게시판", description = "게시판 API")
public class PostsController {

    private final PostsService postsService;

    // 게시판 조회
    @Operation(summary = "게시판 전체 조회")
    @GetMapping("/")
    public ResponseEntity<List<PostsResponseDto>> getPostsList() {
        return ResponseEntity.ok(postsService.getPostsList());
    }

    // 게시글 선택 조회
    @Operation(summary = "게시글 선택하여 조회", description = "{id}에 게시글 번호를 입력하세요.")
    @GetMapping("/{id}")
    public ResponseEntity<PostsResponseDto> getPosts(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok(postsService.getPosts(id, customUserDetails.getMember()));
    }

    // 게시글 작성
    @Operation(summary = "게시글 작성", description = "게시글을 작성한다.")
    @PostMapping("/write")
    public ResponseEntity<PostsResponseDto> createPosts(@RequestBody PostsRequestDto postsRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        PostsResponseDto responseDto = postsService.createPosts(postsRequestDto, customUserDetails.getMember());
        return ResponseEntity.ok(responseDto);
    }
    
    // 게시글 수정
    @Operation(summary = "게시글 수정", description = "게시글 수정하는 .")
    @PutMapping("/{id}")
    public ResponseEntity<PostsResponseDto> updatePosts(@PathVariable Long id, @RequestBody PostsRequestDto postsRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok(postsService.updatePosts(id, postsRequestDto, customUserDetails.getMember()));
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MsgResponseDto> deletePosts(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok(postsService.deletePosts(id, customUserDetails.getMember()));
    }


    // 게시글 좋아요
    @Operation(summary = "게시글 좋아요")
    @PostMapping("/like/{id}")
    public ResponseEntity<MsgResponseDto> savePostsLike(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok(postsService.savePostsLike(id, customUserDetails.getMember()));
    }


}
