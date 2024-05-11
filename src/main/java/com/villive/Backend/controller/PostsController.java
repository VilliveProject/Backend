package com.villive.Backend.controller;

import com.villive.Backend.dto.PostsRequestDto;
import com.villive.Backend.dto.PostsResponseDto;
import com.villive.Backend.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Tag(name = "게시판", description = "게시판 API")
public class PostsController {

    private final PostsService postsService;

    // 게시판 조회
    @GetMapping("/")
    public ResponseEntity<List<PostsResponseDto>> getPostsList() {
        return ResponseEntity.ok(postsService.getPostsList());
    }

    // 게시글 선택 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostsResponseDto> getPosts(@PathVariable Long id){
        return ResponseEntity.ok(postsService.getPosts(id));
    }

    // 게시글 작성
    @Operation(summary = "게시글 작성", description = "게시글을 작성한다.")
    @PostMapping("/write")
    public ResponseEntity<PostsResponseDto> createPosts(@RequestBody PostsRequestDto postsRequestDto, HttpServletRequest request){
        PostsResponseDto responseDto = postsService.createPosts(postsRequestDto, request);
        return ResponseEntity.ok(responseDto);
    }
    
    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostsResponseDto> updatePosts(@PathVariable Long id, @RequestBody PostsRequestDto postsRequestDto, HttpServletRequest request){
        return ResponseEntity.ok(postsService.updatePosts(id, postsRequestDto, request));
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePosts(@PathVariable Long id, HttpServletRequest request){
        postsService.deletePosts(id, request);
        return ResponseEntity.ok().body("게시글이 삭제되었습니다.");
    }
}
